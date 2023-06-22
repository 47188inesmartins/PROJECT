package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.*
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.ICompanyServices
import backend.jvm.utils.UserRoles
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.sql.Date
import java.sql.Time
import java.util.*


@Service
class CompanyServices : ICompanyServices {

    companion object{
        const val NIF_NUMBERS = 9
    }

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var usersRepository: UserRepository

    @Autowired
    lateinit var serviceRepository: ServiceRepository

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var dayRepository: DayRepository

    @Autowired
    lateinit var vacationRepository: VacationRepository

    @Autowired
    lateinit var userCompanyRepository: UserCompanyRepository

    @Autowired
    lateinit var imageRepository: ImageRepository

    private val uploadDirectory: String = "kotlin/backend/files"

    override fun getSearchedCompanies(search: String?): List<CompanyOutputDto>?{
        if(search == "null") return getAllCompanies()
        return companyRepository.getCompanyBySearch("%${search}%")?.map { CompanyOutputDto(it) }
    }

    /**
     * Creates a new company and associates a manager for the company created
     * @param token Identifies user
     * @param company Company receive from frontend
     * @return CompanyOutputDto info aof the saved company
     */
    override fun addCompany(token: String, company: CompanyInputDto): CompanyOutputDto {

        val managerUser = usersRepository.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        company.users?.add(managerUser.id)

        if(company.nif.length != NIF_NUMBERS ) throw InvalidNif()
        if(companyRepository.findCompanyByNif(company.nif) != null) throw NifAlreadyExist()

        val services = company.service?.map { serviceRepository.getReferenceById(it) }
        val companyDb = company.mapToCompanyDto(company, services, null)
        val comp = companyRepository.save(companyDb)

        userCompanyRepository.save(UserCompany(managerUser, comp, UserRoles.MANAGER.name))
        val schedule = Schedule(comp,null,null,null,null)
        scheduleRepository.save(schedule)

        return CompanyOutputDto(comp)
    }

    /**
     * Deletes a company
     * @param id that is the id of the company to be deleted
     * @return boolean true if the company was deleted
     * @exception IllegalArgumentException
     */
    override fun deleteCompany(id: Int): Boolean{
        companyRepository.deleteById(id)
        return true
    }

    override fun getCompanyById(id: Int): CompanyOutputDto {
        val comp = companyRepository.getReferenceById(id)
        //if(comp == null) throw CompanyNotFound()
        return CompanyOutputDto(comp)
    }

    override fun getCompanyByNif(nif: String): Company? {
        return companyRepository.findCompanyByNif(nif) ?: throw CompanyNotFound()
    }

    override fun getAllServicesByCompany(id: Int): List<ServiceOutputDto>{
        return serviceRepository.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    override fun getAllAppointmentsByCompany(id: Int):List<AppointmentOutputDto>{
        companyRepository.findAllById(id) ?: throw CompanyNotFound()
        return appointmentRepository.getAllOnGoingAppointments(id).map { AppointmentOutputDto(it) }
    }

    override fun getAppointmentByCompanyAndDateAndHour(id: Int, date: String, hour: String): List<AppointmentOutputDto>{
        val d = Date.valueOf(date)
        val h = Time.valueOf(hour)
        companyRepository.findAllById(id) ?: throw CompanyNotFound()
        return companyRepository.getAppointmentsByDateAndHour(id, d, h).map { AppointmentOutputDto(it) }
    }


    override fun getOpenDaysByCompany(id: Int): List<DayOutputDto>{
        return dayRepository.getOpenDays(id).map { DayOutputDto(it) }
    }

    override fun getVacationByCompany(id: Int): List<VacationOutputDto>{
        return vacationRepository.getVacationsByCompany(id).map { VacationOutputDto(it) }
    }

    override fun changeAddress(id: Int, address: String){
        companyRepository.findAllById(id) ?: throw CompanyNotFound()
        companyRepository.changeAddress(id, address)
    }

    override fun changeDescription(id: Int, description: String): Company{
        return companyRepository.changeDescription(id, description)
    }


    override fun getAllCompanies(): List<CompanyOutputDto>{
       val a = companyRepository.findAll().map{ CompanyOutputDto(it) }
        return a
    }

    override fun getAllServices(id: Int): List<ServiceOutputDto>{
        return serviceRepository.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    fun getCompanyByUserAndRole(userId: String, role: String): List<CompanyInfo> {
        val user = usersRepository.getUserByToken(UUID.fromString(userId)) ?: throw InvalidCredentials()
        val companyRepository = companyRepository.getCompanyByUserIdAndRole(user.id,role)
        return companyRepository.map {
            CompanyInfo(it.id,it.name)
        }
    }

    fun getAllEmployeesByCompanyAndMoney(cid: Int): List<Pair<UserInfo,Double>>{
        val employees = usersRepository.getUserEmployeesByCompany(cid) ?: throw EmployeeNotFound()
        val dateEnd = Date(System.currentTimeMillis())
        val currentDate = Date(System.currentTimeMillis())
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_MONTH, -30)
        val dateBegin = Date(calendar.timeInMillis)

        val earnedMoney = employees.map {
            Pair(
                UserInfo(it.id,it.name),
                serviceRepository.getEarnedMoneyByEmployee(it.id,cid,dateBegin,dateEnd)?:0.0
            )
        }
        return earnedMoney
    }

    fun getAppointmentsByCompany(cid: Int): List<AppointmentInfoEmployeeEnd>{
        val schedule =  scheduleRepository.getScheduleByCompany_Id(cid)?: throw InvalidSchedule()
        val appointments = appointmentRepository.getAppointmentsBySchedule(schedule.id)
        if(appointments.isEmpty()) return emptyList()

        return appointments.map {
            val service = serviceRepository.getServiceDBByAppointment(it.id)
            val employee  = it.usersDB?.firstOrNull { user ->
                val role = user?.id?.let { it1 -> usersRepository.getUserRoleByCompany(it1,cid) }
                (role == UserRoles.EMPLOYEE.name ||role == UserRoles.MANAGER.name)
            } ?: throw UserNotFound()
            val endHour = Time(it.appHour.time + service.duration.time)
            AppointmentInfoEmployeeEnd(
                it.id,
                it.appHour.toString(),
                endHour.toString(),
                it.appDate,
                employee.name
            )
        }
    }

    /**
     * @param cid
     */
    fun uploadPhoto(cid: Int, images: Array<MultipartFile>){
        val company = companyRepository.findAllById(cid) ?: throw CompanyNotFound()
        images.forEach {image ->
            val fileName = image.originalFilename?.let { StringUtils.cleanPath(it) }
            if(fileName?.contains("..")!!){
                println("not a valid file")
            }
            val encodedFile = image.bytes
            imageRepository.save(Image(encodedFile,company))
        }
    }


    fun getEmployeesByCompany(cid: Int): List<UserOutputDto>{
        val employees = usersRepository.getUserEmployeesByCompany(cid) ?: throw EmployeeNotFound()
        return employees.map { UserOutputDto(it) }
    }

    private fun saveFile(file: MultipartFile, fileName: String): String {
        val filePath: String = (uploadDirectory + File.separator) + fileName
        val targetLocation: Path = Path.of(filePath)
        Files.createDirectories(targetLocation.parent)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
        return filePath
    }


    fun removeEmployeeFromCompany(cid: Int, employeeId: Int){
        userCompanyRepository.deleteAllByCompanyAndUserAndRole(cid, employeeId)
        //remover os appointments deste employee
    }





}