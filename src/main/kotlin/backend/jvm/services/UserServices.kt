package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.appointment.AppointmentInfo
import backend.jvm.model.appointment.AppointmentsUserInfo
import backend.jvm.model.company.CompanyOutputDto
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.user.*
import backend.jvm.dao.*
import backend.jvm.services.interfaces.IUserInterface
import backend.jvm.utils.*
import backend.jvm.utils.errorHandling.*
import backend.jvm.utils.hashUtils.PasswordDecoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import kotlin.collections.List
import java.sql.Date
import java.util.*

@Service
class UserServices : IUserInterface {

    @Autowired
    lateinit var userDao: UserDao
    @Autowired
    lateinit var servicesRepository: ServiceDao
    @Autowired
    lateinit var appointmentDao: AppointmentDao
    @Autowired
    lateinit var companyDao: CompanyDao
    @Autowired
    lateinit var roleDao: RoleDao
    @Autowired
    lateinit var userCompanyDao: UserCompanyDao
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var passwordDecoder: PasswordDecoder

    companion object{
        val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
        val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
    }

    override fun addUser(user: UserInputDto): CreatedUserOutput {
        if(!EMAIL_FORMAT.matches(user.email)) throw InvalidEmail()
        if(!PASSWORD_FORMAT.matches(user.password)) throw PasswordInsecure()
        if(userDao.getUsersByEmail(user.email) != null) throw EmailAlreadyExists()

        val servicesList = mutableListOf<ServiceEntity>()
        val appList =  mutableListOf<AppointmentEntity>()

        if(user.services != null ) user.services.forEach { servicesList.add(servicesRepository.findById(it).get()) }
        if(user.appointment != null ) user.appointment.forEach { appList.add(appointmentDao.findById(it).get()) }
        val role = roleDao.getRoleByName(UserRoles.CLIENT.name)

        val encodePassword = passwordEncoder.encode(user.password)
        val token = passwordEncoder.encode(UUID.randomUUID().toString())
        val returnUser = userDao.save(
            user.mapToUser(
                user,
                encodePassword,
                servicesList,
                appList, null,
                listOf(role), null, user.interests, user.profilePic)
        )
        return CreatedUserOutput(returnUser.id, returnUser.token)
    }

    override fun deleteUser(id: Int): Boolean {
        if(userDao.findById(id).isEmpty) return false
        userDao.deleteById(id)
        return true
    }

    override fun getUserById(token: String): UserOutputDto {
        val getUser = userDao.getUserByToken(UUID.fromString(token)) ?: throw UserNotFound()
        return UserOutputDto(getUser)
    }

    override fun changeRole(id: Int, name: String): String {
        return userDao.changeRole(id,name)
    }

    override fun changePassword(password: String, id: Int): String{
        if(!Hashing.verifyPasswordSecure(password)) throw InvalidPassword()
        val pass = Hashing.encodePass(password)
        return userDao.changePassword(pass, id)
    }

    override fun getRoleByUserAndCompany (compId: Int, userId: Int): String? {
        return userCompanyDao.getRoleByCompanyAndUser(compId,userId)
    }

    override fun getUsersByEmailAndPassword (email: String, password: String): UserOutputDto {
        val user = userDao.getUsersByEmail(email) ?: throw UserNotFound()
        if(user.status == "PENDING") throw UserNotFound()
        val passwordEncode = passwordDecoder.getSavedPassword(user.password,password)
        return if(passwordEncode)  UserOutputDto(user)
        else throw InvalidCredentials()
    }

    override fun getUserByToken(token: String): UserEntity?{
        val t = UUID.fromString(token)
        return userDao.getUserByToken(t) ?: throw InvalidToken()
    }

    fun getRolesByToken(token: String): List<CompanyRole> {
        val user = userDao.getUserByToken(UUID.fromString(token))?.id ?: throw UserNotFound()
        val companyRoleRep = userCompanyDao.getUserCompanyByUserId(user)
        return companyRoleRep.map {
            CompanyRole(it.companyEntity!!.id,it.role)
        }
    }

    override fun addEmployees(companyId: Int, emails: List<String>){
        val user = emails.map {email ->
            userDao.getUsersByEmail(email) ?: throw UserNotFound()
        }
        val company = companyDao.findAllById(companyId) ?: throw CompanyNotFound()

        val newEmployees = user.filter {
            userCompanyDao.getRoleByCompanyAndUser(companyId, it.id) != UserRoles.MANAGER.name
            &&  userCompanyDao.getRoleByCompanyAndUser(companyId, it.id) != UserRoles.EMPLOYEE.name
        }

        newEmployees.forEach {
            userCompanyDao.save(UserCompany(it, company, UserRoles.EMPLOYEE.name))
        }
    }

    override fun getAllAppointmentsByUser(token: String): AppointmentsUserInfo {
        val user = userDao.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val listAppointment = appointmentDao.getAppointmentByUserDB(user.id)
        if(listAppointment.isEmpty()) return AppointmentsUserInfo()

        val currentDate = getCurrentDate()
        val currentTime = getCurrentTime()

        val (latterApp,soonerApp) = listAppointment.partition {
            it.appDate.before(currentDate) && it.appHour < currentTime
        }
        return AppointmentsUserInfo(mapToAppointmentsInfo(soonerApp),mapToAppointmentsInfo(latterApp))
    }

    override fun mapToAppointmentsInfo(listAppointmentEntities: List<AppointmentEntity>):List<AppointmentInfo>{
        if(listAppointmentEntities.isEmpty()) return emptyList()
        return listAppointmentEntities.map {
            val getCompany = companyDao.getCompanyBySchedule(it.schedule.id)?:throw CompanyNotFound()

            if(it.usersDB == null) throw InvalidAppointment()
            val employee = it.usersDB.firstOrNull { user ->
                user?.let { it1 -> userDao.getUserDBByIdAndRole(UserRoles.EMPLOYEE.name, it1.id) } != null ||
                user?.let { it1 -> userDao.getUserDBByIdAndRole(UserRoles.MANAGER.name, it1.id) } != null
            }?: throw UserNotFound()

            AppointmentInfo(
                it.id,
                convertToHourFormat(it.appHour),
                it.appDate,
                getCompany,
                employee.name
            )
        }
    }

    override fun getPersonalizedCompanies(token: String?): List<CompanyOutputDto>?{
        if(token == null) {
            val companies = companyDao.findAll()
            return companies.map { CompanyOutputDto(it) }
        }
        val user = userDao.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val categoriesArray = user.interests.split(",").toTypedArray()
        val comps = companyDao.getCompaniesByCategory(categoriesArray)
        return comps?.map { CompanyOutputDto(it) }
    }

    override fun getRoleByUserIdAndCompany(company: Int, user_id: String): String? {
        val token = userDao.getUserByToken(UUID.fromString(user_id))
        return if (token == null)  null
        else userCompanyDao.getRoleByCompanyAndUser(company, token.id)
    }

    override fun getEarnedMoneyEmployee(userId: String, dateBegin: String, dateEnd: String,company: Int): Double {
        val user = userDao.getUserByToken(UUID.fromString(userId)) ?: throw UserNotFound()
        val getBeginDate = Date.valueOf(dateBegin)
        val getEndDate = Date.valueOf(dateEnd)
        return servicesRepository.getEarnedMoneyByEmployee(user.id,company, getBeginDate, getEndDate) ?: 0.0
    }

    override fun updateUserProfilePicture(id: Int, image: MultipartFile){
        val fileName = image.originalFilename?.let { StringUtils.cleanPath(it) }
        if(fileName?.contains("..")!!){
            println("not a valid file")
        }
        val encodedFile = image.bytes
        userDao.updateUserPicture(id,encodedFile)
    }

    fun validateAccount(email: String){
        userDao.changeStatusByEmail(email)
    }
}