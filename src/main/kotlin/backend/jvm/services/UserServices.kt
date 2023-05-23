package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.*
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.IUserInterface
import backend.jvm.utils.Hashing
import backend.jvm.utils.UserAvailability
import backend.jvm.utils.UserRoles
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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
    lateinit var scheduleRepository: ScheduleRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var userCompanyRepository: UserCompanyRepository



    companion object{
        val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
        val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
    }

    override fun addUser(user: UserInputDto): UserOutputDto {
        if(!EMAIL_FORMAT.matches(user.email)) throw InvalidEmail()
        if(!PASSWORD_FORMAT.matches(user.password)) throw PasswordInsecure()
        if(userRepository.getUsersByEmail(user.email) != null) throw EmailAlreadyExists()

        val servicesList = mutableListOf<ServiceDB>()
        val appList =  mutableListOf<Appointment>()

        if(user.services != null ) user.services.forEach { servicesList.add(servicesRepository.findById(it).get()) }
        if(user.appointment != null ) user.appointment.forEach { appList.add(appointmentRepository.findById(it).get()) }
        val role = roleRepository.getRoleByName(UserRoles.CLIENT.name)

        val returnUser = userRepository.save(
            user.mapToUser(user,Hashing.encodePass(user.password),servicesList,appList, comp, listOf(role))
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

    override fun getRole(id: Int):String?{
        return userRepository.getRole(id)?: throw UserNotFound()
    }

    override fun changeAvailability(availability: String, id: Int): String{
    TODO()
    // return userRepository.changeAvailability(availability,id)
    }


    override fun changePassword(password: String, id: Int): String{
        if(!Hashing.verifyPasswordSecure(password)) throw InvalidPassword()
        val pass = Hashing.encodePass(password)
        return userRepository.changePassword(pass, id)
    }

  /*  fun getUsersByCompId (compId: Int): List<UserOutputDto> {
        val repoList = userRepository.getUsersByCompanyId(compId)
        return repoList.map { UserOutputDto(it) }
    }
*/

    fun getRoleByCompId (compId: Int, userId: Int): UserCompany {
        val user = userRepository.getReferenceById(userId)
        val company = companyRepository.getReferenceById(compId)
        return userCompanyRepository.getRoleByCompanyAndUser(company,user)
    }

    override fun getUsersByEmailAndPass (email: String, password: String): UserOutputDto {
        val user = userRepository.getUsersByEmailPass(Hashing.encodePass(password),email)
            ?: throw InvalidCredentials()
        println("here")
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

    override fun addEmployee(companyId: Int, user: String): UserOutputDto {
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
        return UserOutputDto(updatedUser)
    }

    override fun getAllAppointments(id: Int): List<AppointmentOutputDto>{
        val listAppointment = appointmentRepository.getAppointmentByUserDB(id)
        return listAppointment.map { AppointmentOutputDto(it) }
    }

    override fun changeRole(id: Int, name: String): String {
        return userRepository.changeRole(id,name)
    }
}

/*
fun scheduleAnAppointment(id:Int,appointment: AppointmentInputDto): AppointmentOutputDto {
         val getUser = userRepository.findById(id)
         if(getUser.isEmpty) throw UserNotFound()
         val serviceDb = servicesRepository.findById(appointment.service).get()
         val schedule = scheduleRepository.findById(appointment.schedule).get()
         val app = Appointment(
             appHour = Time.valueOf(appointment.appHour),
             appDate = Date.valueOf(appointment.appDate),
             scheduleId = schedule,
             userDBId = getUser.get(),
             serviceDB = serviceDb
         )
         val com = appointmentRepository.save(app)
         return AppointmentOutputDto(com)
}
*/