package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.model.appointment.AppointmentInfoEmployeeEnd
import backend.jvm.model.appointment.AppointmentOutputDto
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.company.CompanyInfo
import backend.jvm.model.company.CompanyInputDto
import backend.jvm.model.company.CompanyOutputDto
import backend.jvm.model.day.DayOutputDto
import backend.jvm.model.image.ImageEntity
import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.service.ServiceOutputDto
import backend.jvm.model.user.UserInfo
import backend.jvm.model.user.UserOutputDto
import backend.jvm.model.vacation.VacationOutputDto
import backend.jvm.dao.*
import backend.jvm.model.day.DayInputDto
import backend.jvm.model.user.UserEntity
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.ICompanyServices
import backend.jvm.utils.*
import backend.jvm.utils.errorHandling.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.sql.Date
import java.sql.Time
import java.util.*


@Service
class CompanyServices : ICompanyServices {

    companion object{
        const val NIF_NUMBERS = 9
        const val DEFAULT_DISTANCE = 5.5
        const val DEFAUTL_PAGE = 0
        const val DEFAULT_SIZE = 10
    }

    @Autowired
    lateinit var companyDao: CompanyDao
    @Autowired
    lateinit var scheduleDao: ScheduleDao
    @Autowired
    lateinit var userDao : UserDao
    @Autowired
    lateinit var serviceDao: ServiceDao
    @Autowired
    lateinit var appointmentDao: AppointmentDao
    @Autowired
    lateinit var dayDao: DayDao
    @Autowired
    lateinit var vacationDao: VacationDao
    @Autowired
    lateinit var userCompanyDao: UserCompanyDao
    @Autowired
    lateinit var imageDao: ImageDao
    @Autowired
    lateinit var userServices: UserServices
    @Autowired
    lateinit var dayServices: DayServices

    /**
     * Creates a new company and associates a manager for the company created
     * @param token Identifies user
     * @param company Company receive from frontend
     * @return CompanyOutputDto info aof the saved company
     */
    @Transactional(rollbackOn = [Exception::class])
    fun addCompany(token: String, company: CompanyInputDto, emails: List<String>?, days: List<DayInputDto>, duration: String): CompanyOutputDto{

        val managerUser = userDao.getUserByToken(UUID.fromString(token)) ?: throw UserNotFound()
        val coordinates = getCompanyCoordinates(company.street, company.city, company.country)
        require(company.nif.length == NIF_NUMBERS) { throw InvalidNif() }
        require(companyDao.findCompanyByNif(company.nif) == null) { throw NifAlreadyExist() }

        val companyDb = company.mapToCompanyEntity(coordinates, company)
        val comp = companyDao.save(companyDb)

        emails?.let { userServices.addEmployees(comp.id, it) }

        userCompanyDao.save(UserCompany(managerUser, comp, UserRoles.MANAGER.name))
        val schedule = ScheduleEntity(comp, null, null, null, null)
        scheduleDao.save(schedule)

        dayServices.addOpenDays(days, comp.id, duration)

        return CompanyOutputDto(comp)
    }



    /**
     * Deletes a company
     * @param id that is the id of the company to be deleted
     * @return boolean true if the company was deleted
     * @exception IllegalArgumentException
     */
    override fun deleteCompany(id: Int): Boolean{
        companyDao.deleteById(id)
        return true
    }

    /**
     * Gets a company by the given id
     * @param id that represents the company
     * @param CompanyOutputDto info of the company returned
     */
    override fun getCompanyById(id: Int): CompanyOutputDto {
        val comp = companyDao.getReferenceById(id)
        return CompanyOutputDto(comp)
    }

    /**
     * Gets a company by the given id
     * @param nif that represents the company
     * @param CompanyOutputDto info of the company returned
     */
    override fun getCompanyByNif(nif: String): CompanyOutputDto {
       val c =   companyDao.findCompanyByNif(nif) ?: throw CompanyNotFound()
        return CompanyOutputDto(c)
    }

    override fun getAllServicesByCompany(id: Int): List<ServiceOutputDto>{
        return serviceDao.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    override fun getAllAppointmentsByCompany(id: Int):List<AppointmentOutputDto>{
        companyDao.findAllById(id) ?: throw CompanyNotFound()
        return appointmentDao.getAllOnGoingAppointments(id).map { AppointmentOutputDto(it) }
    }

    override fun getAppointmentByCompanyAndDateAndHour(id: Int, date: String, hour: String): List<AppointmentOutputDto>{
        val d = Date.valueOf(date)
        val h = Time.valueOf(hour)
        companyDao.findAllById(id) ?: throw CompanyNotFound()
        return companyDao.getAppointmentsByDateAndHour(id, d, h).map { AppointmentOutputDto(it) }
    }

    override fun getOpenDaysByCompany(id: Int): List<DayOutputDto>{
        return dayDao.getOpenDays(id).map { DayOutputDto(it) }
    }

    override fun getVacationByCompany(id: Int): List<VacationOutputDto>{
        val schedule = scheduleDao.getScheduleByCompany_Id(id) ?: throw ScheduleNotFound()
        return vacationDao.getVacationsByScheduleId(schedule.id).map { VacationOutputDto(it) }
    }

    override fun changeAddress(id: Int, address: String){
        companyDao.findAllById(id) ?: throw CompanyNotFound()
        companyDao.changeAddress(id, address)
    }

    override fun changeDescription(id: Int, description: String): CompanyEntity {
        return companyDao.changeDescription(id, description)
    }


     fun getAllCompanies(page: Int, size: Int): Page<CompanyOutputDto> {
        val pageable: Pageable = PageRequest.of(page, size)
        return companyDao.findAll(pageable).map { CompanyOutputDto(it) }
    }

    override fun getAllServices(id: Int): List<ServiceOutputDto>{
        return serviceDao.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }



    fun getCompanyByUserAndRole(userId: String, role: String): List<CompanyInfo> {
        val user = userDao.getUserByToken(UUID.fromString(userId)) ?: throw InvalidCredentials()
        val companyRepository = companyDao.getCompanyByUserIdAndRole(user.id,role)
        return companyRepository.map {
            CompanyInfo(it.id,it.name)
        }
    }

    fun getAllEmployeesByCompanyAndMoney(cid: Int): List<Pair<UserInfo,Double>>{
        val employees = userDao.getUsersEmployeesByCompany(cid) ?: throw EmployeeNotFound()
        val dateEnd = Date(System.currentTimeMillis())
        val earnedMoney = employees.map {
            Pair(
                UserInfo(it.id,it.name),
                serviceDao.getEarnedMoneyByEmployee(it.id,cid,getDateForLastDays(),dateEnd)?:0.0
            )
        }
        return earnedMoney
    }

    fun getEarnedMoneyByEmployee(employee: Int, company: Int): Double {
        return serviceDao.getEarnedMoneyByEmployee(
            employee,
            company,
            getDateForLastDays(),
            Date(System.currentTimeMillis())) ?:0.0
    }

    /**
     * returns the date 30 days ago
     * @return Date that is the date of 30 days ago
     */
    private fun getDateForLastDays(): Date{
        val currentDate = Date(System.currentTimeMillis())
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_MONTH, -30)
        return Date(calendar.timeInMillis)
    }

    fun getAppointmentsByCompany(cid: Int): List<AppointmentInfoEmployeeEnd>{
        val schedule =  scheduleDao.getScheduleByCompany_Id(cid)?: throw InvalidSchedule()
        val appointments = appointmentDao.getAppointmentsBySchedule(schedule.id)
        if(appointments.isEmpty()) return emptyList()
        return appointments.map {
            val service = serviceDao.getServiceDBByAppointment(it.id)
            val employee  = it.user?.firstOrNull { user ->
                val role = user?.id?.let { it1 -> userDao.getUserRoleByCompany(it1,cid) }
                (role == UserRoles.EMPLOYEE.name ||role == UserRoles.MANAGER.name)
            } ?: throw UserNotFound()
            val clientFind = it.user.find { cli -> cli != employee }
            val client = clientFind?.name ?: "Scheduled by employee"
            val endHour = Time(it.appHour.time + service.duration.time)
            AppointmentInfoEmployeeEnd(
                it.id,
                it.appHour.toString(),
                endHour.toString(),
                it.appDate,
                "employee: ${employee.name} client: $client"
            )
        }
    }

    /**
     * Saves the uploaded image and associates them with the company
     * @param cid company id
     * @param images array with the images received
     */
    fun uploadPhoto(cid: Int, images: Array<MultipartFile>){
        val company = companyDao.findAllById(cid) ?: throw CompanyNotFound()
        images.forEach {image ->
            val fileName = image.originalFilename?.let { StringUtils.cleanPath(it) }
            if(fileName?.contains("..")!!){
                println("not a valid file")
            }
            val encodedFile = image.bytes
            imageDao.save(ImageEntity(encodedFile,company))
        }
    }

    /**
     * Gets the list of the employees that works for the specified company
     * @param cid indicates the company id
     * @return List<UserOutputDto> returns the list of employees of the company
     */
    fun getEmployeesByCompany(cid: Int): List<UserOutputDto>{
        val employees = userDao.getUsersEmployeesByCompany(cid) ?: throw EmployeeNotFound()
        return employees.map { UserOutputDto(it) }
    }

    /**
     * Removes the association with a company and a user and
     * deletes all future appointments of that user
     * @param cid Company id
     * @param employeeId users id
     */
    fun removeEmployeeFromCompany(cid: Int, employeeId: Int){
        userCompanyDao.deleteAllByCompanyAndUserAndRole(cid, employeeId)
        appointmentDao.deleteAppointmentByDateAndEmployee(employeeId,getCurrentTime(),getCurrentDate())
    }

     fun getSearchedCompanies(distance: Double?,token: String?,search: String?, page: Int, size: Int): Page<CompanyOutputDto>?{
        val getDistance = distance ?: DEFAULT_DISTANCE
        val pageable: Pageable = PageRequest.of(page, size)
        if(search == "null") return getAllCompanies(page, size)
        val allCompanies = companyDao.getCompanyBySearch("%${search}%", pageable)
        if(token == null) return allCompanies?.map { CompanyOutputDto(it) }
        val user = userDao.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val userLocation = Geolocation(user.latitude,user.longitude)
        return getCompaniesByUserLocation(getDistance,userLocation,allCompanies!!,true, pageable).map { CompanyOutputDto(it) }
    }

     fun getPersonalizedCompanies(distance: Double?,token: String?,  page: String?, size: String?): Page<CompanyOutputDto>?{
         val getPag = page?.toIntOrNull() ?: DEFAUTL_PAGE
         val getSize = size?.toIntOrNull() ?: DEFAULT_SIZE
         val getDistance = distance ?: DEFAULT_DISTANCE
         val pageable: Pageable = PageRequest.of(getPag, getSize)
        if(token == null) {
            val companies = companyDao.findAll(pageable)
            return companies.map { CompanyOutputDto(it) }
        }
        val user = userDao.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val userLocation = Geolocation(user.latitude,user.longitude)
        val categoriesArray = user.interests.split(",").toTypedArray()
        val comps = companyDao.getCompaniesByCategory(categoriesArray, pageable)!!
        return getCompaniesByUserLocation(getDistance,userLocation,comps,false, pageable).map { CompanyOutputDto(it) }
    }

    /**
     * Gets the companies that has a distance of 6 kms of the user
     * @param userLocation that contains the coordinates of the user address
     * @param companiesList the companies receive
     * @param order true if we want to show all companies order by distance or false if only the companies nearby the user
     * @return nearby companies
     */

    fun getCompaniesByUserLocation(
        distance: Double,
        userLocation: Geolocation,
        companiesList: Page<CompanyEntity>,
        order: Boolean,
        pageable: Pageable
    ): Page<CompanyEntity> {

        val nearCompanies = mutableListOf<Pair<CompanyEntity, Double>>()

        companiesList.forEach { company ->
            val companyLocation = Geolocation(company.latitude, company.longitude)
            val distanceUserComp = GeoCoder().calculateHaversineDistance(userLocation, companyLocation)
            if (distanceUserComp <= distance) {
                nearCompanies.add(0, Pair(company, distanceUserComp))
            } else {
                if (order) nearCompanies.add(Pair(company, distanceUserComp))
            }
        }

        nearCompanies.sortBy { it.second }
        println(companiesList)
        val totalPages = companiesList.totalElements
        val pagedCompanies = nearCompanies.map { it.first }
        return PageImpl(pagedCompanies, pageable, totalPages)
    }

    /**
     * With the address company its calculated  the coordinates
     * @param street
     * @param city
     * @param country
     * @return Geolocation returns an object with the latitude and longitude of the company
     * @throws InvalidAddress if there's no coordinates for that address
     */
    private fun getCompanyCoordinates(street: String,city: String,country: String):Geolocation{
        val getAddressInfo = AddressInformation(street,city,country)
        return GeoCoder().getGeolocation(getAddressInfo) ?: throw InvalidAddress()
    }

    private fun defaultImages(company: CompanyEntity): List<ImageEntity> {
        val file = File("backend/jvm/utils/File/images.png")
        val image = ImageEntity(file.readBytes(),company)
        return listOf(image,image,image,image)
    }
}