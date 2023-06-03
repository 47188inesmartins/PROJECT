package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.*
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.IUserInterface
import backend.jvm.utils.*
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.sql.Time
import java.util.*
import kotlin.collections.List


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
            user.mapToUser(user,Hashing.encodePass(user.password),servicesList,appList, null, listOf(role), null)
        )

        return CreatedUserOutput(returnUser.id, returnUser.token)
    }

    override fun deleteUser(id: Int): Boolean {
        if(userRepository.findById(id).isEmpty) return false
        userRepository.deleteById(id)
        return true
    }

    override fun getUserById(id: Int): UserOutputDto {
        val getUser = userRepository.findById(id)
        if(getUser.isEmpty) throw UserNotFound()
        return UserOutputDto(getUser.get())
    }

    override fun changeRole(id: Int, name: String): String {
        return userRepository.changeRole(id,name)
    }

 /*   override fun getRoleByUser(id: Int):String?{
        return userRepository.getRole(id)?: throw UserNotFound()
    }
*/
    override fun changePassword(password: String, id: Int): String{
        if(!Hashing.verifyPasswordSecure(password)) throw InvalidPassword()
        val pass = Hashing.encodePass(password)
        return userRepository.changePassword(pass, id)
    }

    override fun getRoleByUserAndCompany (compId: Int, userId: Int): UserCompany {
        val user = userRepository.getReferenceById(userId)
        val company = companyRepository.getReferenceById(compId)
        return userCompanyRepository.getRoleByCompanyAndUser(company,user)
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

    fun getRolesByToken(token: String): List<URoles>? {
        val user = userRepository.getUserByToken(UUID.fromString(token))?.id ?: throw UserNotFound()
        return  userRepository.getRoles(user)
    }



    override fun addEmployee(companyId: Int, user: String): CreatedUserOutput {
        val user = userRepository.getUsersByEmail(user) ?: throw UserNotFound()
        val company = companyRepository.findAllById(companyId) ?: throw CompanyNotFound()

        val name = roleRepository.getRoleByUserId(user.id)
        val userRole = userCompanyRepository.findByCompanyAndUser(company, user)
        if(userRole != null && userRole.role == UserRoles.MANAGER.name) throw AlreadyCompanyManager()
        if(name == UserRoles.EMPLOYEE.name) throw AlreadyEmployee()
        if(name != UserRoles.CLIENT.name) throw InvalidUser()

        userRepository.changeRole(user.id,UserRoles.EMPLOYEE.name)
        userRepository.changeAvailabilityAndMaxNumber(UserAvailability.AVAILABLE.name, 1, user.id)

        userCompanyRepository.save(UserCompany(user, company, UserRoles.EMPLOYEE.name))

        val updatedUser = userRepository.getUserById(user.id)!!
        return CreatedUserOutput(updatedUser.id, updatedUser.token)
    }

    override fun getAllAppointmentsByUser(id: Int): AppointmentsUserInfo{
        val listAppointment = appointmentRepository.getAppointmentByUserDB(id)
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
                userRepository.getUserDBByIdAndRole(UserRoles.EMPLOYEE.name,user.id)!= null ||
                userRepository.getUserDBByIdAndRole(UserRoles.MANAGER.name,user.id)!= null
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
}