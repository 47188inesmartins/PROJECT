package backend.jvm.services

import backend.jvm.model.Appointment
import backend.jvm.model.ServiceDB
import backend.jvm.model.User
import backend.jvm.repository.AppointmentRepository
import backend.jvm.repository.CompanyRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.UserInputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.utils.Hashing
import backend.jvm.utils.UserRoles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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

    fun addUser(user: UserInputDto): UserOutputDto {
        val servicesList = mutableListOf<ServiceDB>()
        val appList =  mutableListOf<Appointment>()

        if(user.services != null ) user.services.forEach { servicesList.add(servicesRepository.findById(it).get()) }
        if(user.appointment != null ) user.appointment.forEach { appList.add(appointmentRepository.findById(it).get()) }
        val comp = if(user.companyId != null )companyRepository.findById(user.companyId).get() else null

        val returnUser = userRepository.save(
            user.mapToUser(user,Hashing.encodePass(user.password),servicesList,appList,comp)
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
        if(getUser.isEmpty) throw Exception("the user doesn´t exists")
        return UserOutputDto(getUser.get())
    }

    fun getRole(id: Int):String?{
        return userRepository.getRole(id)
    }

    fun changeRole(id: Int, roleName: String): String{
        UserRoles.valueOf(roleName.uppercase(Locale.getDefault()))
        return userRepository.changeRole(id, roleName)
    }

    fun changeAvailability(availability: String, id: Int): String{
        return userRepository.changeAvailability(availability,id)
    }

    fun findById(id:Int): User {
        return userRepository.findById(id).get()
    }

    fun changePassword(password: String, id: Int): String{
        if(!Hashing.verifyPasswordSecure(password)) throw   Exception("Password isn´t save")
        val pass = Hashing.encodePass(password)
        return userRepository.changePassword(pass, id)
    }

    fun getUsersByCompId (compId: Int): List<UserOutputDto> {
        val repoList = userRepository.getUsersByCompanyId(compId)
        return repoList.map { UserOutputDto(it) }
    }

    fun getUsersByEmailAndPass (email: String, password: String): UserOutputDto {
        val user = userRepository.getUsersByEmailPass(Hashing.encodePass(password),email)
            ?: throw Exception("Invalid email or password")
        println("here")
        return UserOutputDto(user)
    }

    fun scheduleAnAppointment(id:Int,appointment: AppointmentInputDto): AppointmentOutputDto {
        val getUser = userRepository.findById(id)
        if(getUser.isEmpty) throw Exception("Invalid user")
        val serviceDb = servicesRepository.findById(appointment.service).get()
        val schedule = scheduleRepository.findById(appointment.schedule).get()
        val app = Appointment(
            appHour = Time.valueOf(appointment.appHour),
            appDate = Date.valueOf(appointment.appDate),
            scheduleId = schedule,
            userId = getUser.get(),
            serviceDB = serviceDb
        )
        val com = appointmentRepository.save(app)
        return AppointmentOutputDto(com)
    }

}