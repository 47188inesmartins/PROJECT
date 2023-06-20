package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.*
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.IUserInterface
import backend.jvm.utils.*
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.collections.List
import java.sql.Date
import java.util.*

@Service
class UserServices : IUserInterface {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var servicesRepository: ServiceRepository
    @Autowired
    lateinit var appointmentRepository: AppointmentRepository
    @Autowired
    lateinit var companyRepository: CompanyRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var userCompanyRepository: UserCompanyRepository

    companion object{
        val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
        val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
    }

    override fun addUser(user: UserInputDto): CreatedUserOutput {
        if(!EMAIL_FORMAT.matches(user.email)) throw InvalidEmail()
        if(!PASSWORD_FORMAT.matches(user.password)) throw PasswordInsecure()
        if(userRepository.getUsersByEmail(user.email) != null) throw EmailAlreadyExists()

        val servicesList = mutableListOf<ServiceDB>()
        val appList =  mutableListOf<Appointment>()

        if(user.services != null ) user.services.forEach { servicesList.add(servicesRepository.findById(it).get()) }
        if(user.appointment != null ) user.appointment.forEach { appList.add(appointmentRepository.findById(it).get()) }
        val role = roleRepository.getRoleByName(UserRoles.CLIENT.name)

        val returnUser = userRepository.save(
            user.mapToUser(user,Hashing.encodePass(user.password),servicesList,appList, null, listOf(role), null, user.interests)
        )

        return CreatedUserOutput(returnUser.id, returnUser.token)
    }

    override fun deleteUser(id: Int): Boolean {
        if(userRepository.findById(id).isEmpty) return false
        userRepository.deleteById(id)
        return true
    }

    override fun getUserById(token: String): UserOutputDto {
        val getUser = userRepository.getUserByToken(UUID.fromString(token)) ?: throw UserNotFound()
        return UserOutputDto(getUser)
    }

    override fun changeRole(id: Int, name: String): String {
        return userRepository.changeRole(id,name)
    }

    override fun changePassword(password: String, id: Int): String{
        if(!Hashing.verifyPasswordSecure(password)) throw InvalidPassword()
        val pass = Hashing.encodePass(password)
        return userRepository.changePassword(pass, id)
    }

    override fun getRoleByUserAndCompany (compId: Int, userId: Int): String? {
        return userCompanyRepository.getRoleByCompanyAndUser(compId,userId)
    }

    override fun getUsersByEmailAndPassword (email: String, password: String): UserOutputDto {
        val user = userRepository.getUsersByEmailPass(Hashing.encodePass(password),email)
            ?: throw InvalidCredentials()
        return UserOutputDto(user)
    }

    override fun getUserByToken(token: String):UserDB?{
        val t = UUID.fromString(token)
        return userRepository.getUserByToken(t) ?: throw InvalidToken()
    }

    fun getRoleByToken(token: String): String? {
        val user = userRepository.getUserByToken(UUID.fromString(token))?.id ?: throw UserNotFound()
        return  userRepository.getRole(user)
    }

    fun getRolesByToken(token: String): List<CompanyRole> {
        val user = userRepository.getUserByToken(UUID.fromString(token))?.id ?: throw UserNotFound()
        val companyRoleRep = userCompanyRepository.getUserCompanyByUserId(user)
        return companyRoleRep.map {
            CompanyRole(it.company!!.id,it.role)
        }
    }



    override fun addEmployees(companyId: Int, emails: List<String>){
        val user = emails.map {email ->
            userRepository.getUsersByEmail(email) ?: throw UserNotFound()
        }
        val company = companyRepository.findAllById(companyId) ?: throw CompanyNotFound()

        val newEmployees = user.filter {
            userCompanyRepository.getRoleByCompanyAndUser(companyId, it.id) != UserRoles.MANAGER.name
            &&  userCompanyRepository.getRoleByCompanyAndUser(companyId, it.id) != UserRoles.EMPLOYEE.name
        }

        newEmployees.forEach {
            //userRepository.changeAvailabilityAndMaxNumber(UserAvailability.AVAILABLE.name, 1, it.id)
            userCompanyRepository.save(UserCompany(it, company, UserRoles.EMPLOYEE.name))
        }

    }

    override fun getAllAppointmentsByUser(token: String): AppointmentsUserInfo{
        val user = userRepository.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val listAppointment = appointmentRepository.getAppointmentByUserDB(user.id)
        if(listAppointment.isEmpty()) return AppointmentsUserInfo()

        val currentDate = getCurrentDate()
        val currentTime = getCurrentTime()

        val (latterApp,soonerApp) = listAppointment.partition {
            it.appDate.before(currentDate) && it.appHour < currentTime
        }
        return AppointmentsUserInfo(mapToAppointmentsInfo(soonerApp),mapToAppointmentsInfo(latterApp))
    }

    fun mapToAppointmentsInfo(listAppointments: List<Appointment>):List<AppointmentInfo>{
        if(listAppointments.isEmpty()) return emptyList()
        return listAppointments.map {
            val getCompany = companyRepository.getCompanyBySchedule(it.schedule.id)?:throw CompanyNotFound()

            if(it.usersDB == null) throw InvalidAppointment()
            val employee = it.usersDB.firstOrNull { user ->
                user?.let { it1 -> userRepository.getUserDBByIdAndRole(UserRoles.EMPLOYEE.name, it1.id) } != null ||
                user?.let { it1 -> userRepository.getUserDBByIdAndRole(UserRoles.MANAGER.name, it1.id) } != null
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

    fun getPersonalizedCompanies(token: String?): List<CompanyOutputDto>?{
        if(token == null) {
            val companys = companyRepository.findAll()
            return companys.map { CompanyOutputDto(it) }
        }
        val user = userRepository.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val categoriesArray = user.interests.split(",").toTypedArray()
        val comps = companyRepository.getCompaniesByCategory(categoriesArray)
        return comps?.map { CompanyOutputDto(it) }
    }


    fun getRoleByUserIdAndCompany(company: Int, user_id: String): String? {
        val token = userRepository.getUserByToken(UUID.fromString(user_id))
        return if (token == null)  null
        else userCompanyRepository.getRoleByCompanyAndUser(company, token.id)
    }

    fun getEarnedMoneyEmployee(userId: String, dateBegin: String, dateEnd: String,company: Int): Double {
        val user = userRepository.getUserByToken(UUID.fromString(userId)) ?: throw UserNotFound()
        val getBeginDate = Date.valueOf(dateBegin)
        val getEndDate = Date.valueOf(dateEnd)
        return servicesRepository.getEarnedMoneyByEmployee(user.id,company, getBeginDate, getEndDate) ?: 0.0
    }

}