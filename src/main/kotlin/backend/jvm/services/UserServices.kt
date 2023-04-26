package backend.jvm.services

import backend.jvm.model.Appointment
import backend.jvm.model.ServiceDB
import backend.jvm.model.UserDB
import backend.jvm.repository.*
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.UserInputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.utils.Hashing
import backend.jvm.utils.UserAvailability
import backend.jvm.utils.UserRoles
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.sql.Date
import java.util.*
import kotlin.collections.List


@Service
class UserServices {

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

    companion object{
        val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
        val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
    }

    fun addUser(user: UserInputDto): UserOutputDto {
        if(!EMAIL_FORMAT.matches(user.email)) throw InvalidEmail()
        if(!PASSWORD_FORMAT.matches(user.password)) throw PasswordInsecure()

        if(userRepository.getUsersByEmail(user.email) != null)
            throw EmailAlreadyExists()

        val servicesList = mutableListOf<ServiceDB>()
        val appList =  mutableListOf<Appointment>()

        if(user.services != null ) user.services.forEach { servicesList.add(servicesRepository.findById(it).get()) }
        if(user.appointment != null ) user.appointment.forEach { appList.add(appointmentRepository.findById(it).get()) }
        val comp = if(user.companyId != null ) companyRepository.findById(user.companyId).get() else null
        val a = UserRoles.CLIENT.name
        val b = UserAvailability.AVAILABLE.name
        val role = roleRepository.getRoleByName(UserRoles.CLIENT.name)

        val returnUser = userRepository.save(
            user.mapToUser(user,Hashing.encodePass(user.password),servicesList,appList, comp, listOf(role))
        )

        return UserOutputDto(returnUser)
    }

    fun deleteUser(id: Int): Boolean {
        if(userRepository.findById(id).isEmpty) return false
        userRepository.deleteById(id)
        return true
    }

    fun getUserById(id: Int): UserOutputDto {
        val getUser = userRepository.findById(id)
        if(getUser.isEmpty) throw UserNotFound()
        return UserOutputDto(getUser.get())
    }

    fun getRole(id: Int):String?{
        return userRepository.getRole(id)?: throw UserNotFound()
    }

    fun changeAvailability(availability: String, id: Int): String{
        return userRepository.changeAvailability(availability,id)
    }


    fun changePassword(password: String, id: Int): String{
        if(!Hashing.verifyPasswordSecure(password)) throw InvalidPassword()
        val pass = Hashing.encodePass(password)
        return userRepository.changePassword(pass, id)
    }

    fun getUsersByCompId (compId: Int): List<UserOutputDto> {
        val repoList = userRepository.getUsersByCompanyId(compId)
        return repoList.map { UserOutputDto(it) }
    }

    fun getUsersByEmailAndPass (email: String, password: String): UserOutputDto {
        val user = userRepository.getUsersByEmailPass(Hashing.encodePass(password),email)
            ?: throw InvalidCredentials()
        println("here")
        return UserOutputDto(user)
    }



    fun getUserByToken(token: String):UserDB?{
        val t = UUID.fromString(token)
        return userRepository.getUserByToken(t) ?: throw InvalidToken()
    }

    fun getRoleByToken(token: String): String? {
        val user = userRepository.getUserByToken(UUID.fromString(token))?.id ?: throw UserNotFound()
        return  userRepository.getRole(user)
    }

    fun addEmployee(id: Int, user: String): UserOutputDto{
        val getUser = userRepository.getUsersByEmail(user) ?: throw UserNotFound()
        companyRepository.findAllById(id) ?: throw CompanyNotFound()

        val name = roleRepository.getRoleByUserId(getUser.id)
        if(name == UserRoles.EMPLOYEE.name) throw AlreadyEmployee()
        if(name != UserRoles.CLIENT.name) throw InvalidUser()

        userRepository.changeRole(getUser.id,UserRoles.EMPLOYEE.name)
        userRepository.changeAvailability(UserAvailability.AVAILABLE.name,getUser.id)
        userRepository.changeMaxNumber(1,getUser.id)
        userRepository.changeCompany(id,getUser.id)

        val updatedUser = userRepository.getUserById(getUser.id)!!
        return UserOutputDto(updatedUser)
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
}