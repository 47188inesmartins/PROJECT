package backend.jvm.services

import backend.jvm.model.Appointment
import backend.jvm.model.Company
import backend.jvm.model.Services
import backend.jvm.model.User
import backend.jvm.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServices {

    @Autowired
    lateinit var userRepository: UserRepository

    fun addUser(user: User): User {
        return userRepository.save(user)
    }

    fun deleteUser(id: Int): Boolean{
        userRepository.deleteById(id)
        return true
    }

    fun getUserById(id: Int): Optional<User> {
        return userRepository.findById(id)
    }

    fun getUserByEmail(email: String): User{
        return userRepository.getUsersByEmail(email)
    }

    fun getRole(id: Int):String{
        return userRepository.getRole(id)
    }

    fun changeRole(id: Int, roleName: String): User{
        return userRepository.changeRole(id, roleName)
    }

    fun changeAvailability(availability: String, id: Int): User{
        return userRepository.changeAvailability(availability,id)
    }

    fun changePassword(password: String, id: Int): User{
        return userRepository.changePassword(password, id)
    }

    fun getUsersByServices (service: List<Services>): List<User>{
        return userRepository.getUsersByServices(service)
    }

    fun getUsersByAppointment(appointment: List<Appointment>): List<User>{
        return userRepository.getUsersByAppointment(appointment)
    }

    fun getUsersByCompId (compId: Company): User{
        return userRepository.getUsersByCompId(compId)
    }
}