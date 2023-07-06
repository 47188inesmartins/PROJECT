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
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.ICompanyServices
import backend.jvm.utils.*
import backend.jvm.utils.errorHandling.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.sql.Date
import java.sql.Time
import java.util.*


@Service
class CompanyServices : ICompanyServices {

    companion object{
        const val NIF_NUMBERS = 9
    }

    @Autowired
    lateinit var companyDao: CompanyDao
    @Autowired
    lateinit var scheduleDao: ScheduleDao
    @Autowired
    lateinit var usersRepository: UserDao
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
    lateinit var userDao: UserDao

    /**
     * Creates a new company and associates a manager for the company created
     * @param token Identifies user
     * @param company Company receive from frontend
     * @return CompanyOutputDto info aof the saved company
     */
    @Transactional(rollbackOn = [Exception::class])
    override fun addCompany(token: String, company: CompanyInputDto): CompanyOutputDto {

        val managerUser = usersRepository.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        company.users?.add(managerUser.id)

        val coordinates = getCompanyCoordinates(company.street,company.city,company.country)

        if(company.nif.length != NIF_NUMBERS ) throw InvalidNif()
        if(companyDao.findCompanyByNif(company.nif) != null) throw NifAlreadyExist()

        val services = company.service?.map { serviceDao.getReferenceById(it) }
        val companyDb = company.mapToCompanyDto(coordinates,company, services, null)
        val comp = companyDao.save(companyDb)

        userCompanyDao.save(UserCompany(managerUser, comp, UserRoles.MANAGER.name))
        val schedule = ScheduleEntity(comp,null,null,null,null)
        scheduleDao.save(schedule)

        return CompanyOutputDto(comp)
    }

    private fun getCompanyCoordinates(street: String,city: String,country: String):Geolocation{
        val getAddressInfo = AddressInformation(street,city,country)
        return GeoCoder().getGeolocation(getAddressInfo) ?: throw InvalidAddress()
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


    override fun getAllCompanies(): List<CompanyOutputDto>{
       val a = companyDao.findAll().map{ CompanyOutputDto(it) }
        return a
    }

    override fun getAllServices(id: Int): List<ServiceOutputDto>{
        return serviceDao.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    fun getCompanyByUserAndRole(userId: String, role: String): List<CompanyInfo> {
        val user = usersRepository.getUserByToken(UUID.fromString(userId)) ?: throw InvalidCredentials()
        val companyRepository = companyDao.getCompanyByUserIdAndRole(user.id,role)
        return companyRepository.map {
            CompanyInfo(it.id,it.name)
        }
    }

    fun getAllEmployeesByCompanyAndMoney(cid: Int): List<Pair<UserInfo,Double>>{
        val employees = usersRepository.getUsersEmployeesByCompany(cid) ?: throw EmployeeNotFound()
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
        val employees = usersRepository.getUsersEmployeesByCompany(cid) ?: throw EmployeeNotFound()
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

    override fun getSearchedCompanies(token: String?,search: String?): List<CompanyOutputDto>?{
        if(search == "null") return getAllCompanies()
        val allCompanies = companyDao.getCompanyBySearch("%${search}%")
        if(token == null) return  allCompanies?.map { CompanyOutputDto(it) }
        val user = userDao.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val userLocation = Geolocation(user.latitude,user.longitude)
        return getCompaniesByUserLocation(userLocation,allCompanies!!,true).map { CompanyOutputDto(it) }
    }

    override fun getPersonalizedCompanies(token: String?): List<CompanyOutputDto>?{
        if(token == null) {
            val companies = companyDao.findAll()
            return companies.map { CompanyOutputDto(it) }
        }
        val user = userDao.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val userLocation = Geolocation(user.latitude,user.longitude)
        val categoriesArray = user.interests.split(",").toTypedArray()
        val comps = companyDao.getCompaniesByCategory(categoriesArray)!!
        return getCompaniesByUserLocation(userLocation,comps,false).map { CompanyOutputDto(it) }
    }

    /**
     * Gets the companies that has a distance of 5.5 kms of the user
     * @param userLocation that containes the coordinates of the user address
     * @param companiesList the companies receive
     * @param order true if we want to show all companies order by distance or only the companies nearby the user
     * @return nearby companies
     */
    private fun getCompaniesByUserLocation(userLocation: Geolocation, companiesList: List<CompanyEntity>,order:Boolean): List<CompanyEntity> {
        val nearCompanies = mutableListOf<CompanyEntity>()
        val distance = 5.5
        companiesList.forEach {company ->
            val companyLocation = Geolocation(company.latitude,company.longitude)
            val distanceUserComp = GeoCoder().calculateHaversineDistance(userLocation,companyLocation)
            if(distanceUserComp <= distance){
                nearCompanies.add(0,company)
            }else{
                if(order) nearCompanies.add(company)
            }
        }
        return nearCompanies
    }

    private fun mergeSort(list: List<Int>): List<Int> {
        if (list.size <= 1) {
            return list
        }

        val middle = list.size / 2
        var left = list.subList(0,middle);
        var right = list.subList(middle,list.size);

        return merge(mergeSort(left), mergeSort(right))
    }
    private fun merge(left: List<Int>, right: List<Int>): List<Int>  {
        var indexLeft = 0
        var indexRight = 0
        var newList : MutableList<Int> = mutableListOf()

        while (indexLeft < left.count() && indexRight < right.count()) {
            if (left[indexLeft] <= right[indexRight]) {
                newList.add(left[indexLeft])
                indexLeft++
            } else {
                newList.add(right[indexRight])
                indexRight++
            }
        }

        while (indexLeft < left.size) {
            newList.add(left[indexLeft])
            indexLeft++
        }

        while (indexRight < right.size) {
            newList.add(right[indexRight])
            indexRight++
        }
        return newList;
    }
}