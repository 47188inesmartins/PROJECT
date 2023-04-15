package backend.jvm.controllers

import backend.jvm.model.User
import backend.jvm.services.UserServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userServices: UserServices

    @PostMapping
    fun addUser(@RequestBody user: User): User{
        println(user.email+user.password+user.birthday+user.availability)
        return userServices.addUser(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): Boolean{
        return userServices.deleteUser(id)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): Optional<User> {
        return userServices.getUserById(id)
}

    @GetMapping("/{id}/role")
    fun getRole(@PathVariable id: Int): String? {
        return userServices.getRole(id)
    }

    @PutMapping("/{id}/role")
    fun changeRole(@PathVariable id: Int, @RequestBody roleName: String): User{
        return userServices.changeRole(id, roleName)
    }

    @PutMapping("/{id}/availability")
    fun changeAvailability(@PathVariable id: Int, @RequestBody availability: String): User{
        return userServices.changeAvailability(availability, id)
    }

    @PutMapping("/{id}/password")
    fun changePassword(@PathVariable id: Int, @RequestBody password: String): User{
        return userServices.changePassword(password, id)
    }
}


