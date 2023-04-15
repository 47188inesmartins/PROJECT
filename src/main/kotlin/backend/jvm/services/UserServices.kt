package backend.jvm.services

import backend.jvm.model.User
import backend.jvm.repository.UserRepository
import backend.jvm.utils.Hashing
import backend.jvm.utils.UserRoles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServices {

    @Autowired
    lateinit var userRepository: UserRepository

    fun addUser(user: User): User {
        //if(!EMAIL_FORMAT.matches(user.email)) throw Exception("Invalid email format")
        val pass = Hashing().encodePass(user.password) ?: throw Exception("Password isn't save")
        user.password = pass
        return userRepository.save(user)
    }

    fun deleteUser(id: Int): Boolean{
        userRepository.deleteById(id)
        return true
    }

    fun getUserById(id: Int): Optional<User> {
        return userRepository.findById(id)
    }

    /*fun getUserByEmail(email: String): User{
        return userRepository.getUsersByEmail(email)
    }*/

    fun getRole(id: Int):String?{
        return userRepository.getRole(id)
    }

    fun changeRole(id: Int, roleName: String): User{
        UserRoles.valueOf(roleName.uppercase(Locale.getDefault()))
        return userRepository.changeRole(id, roleName)
    }

    fun changeAvailability(availability: String, id: Int): User{
        return userRepository.changeAvailability(availability,id)
    }

    fun changePassword(password: String, id: Int): User{
        val pass = Hashing().encodePass(password) ?: throw Exception("Password isn't save")
        return userRepository.changePassword(pass, id)
    }

    /*fun getUsersByServices (service: List<Services>): List<User>{
        return userRepository.getUsersByServices(service)
    }*/

    /*fun getUsersByAppointment(appointment: List<Appointment>): List<User>{
        return userRepository.getUsersByAppointment(appointment)
    }

    fun getUsersByCompId (compId: Company): User{
        return userRepository.getUsersByCompId(compId)
    }*/
}