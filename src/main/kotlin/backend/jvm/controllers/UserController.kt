package backend.jvm.controllers

import backend.jvm.model.User
import backend.jvm.services.UserServices
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.UserInputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.utils.RoleManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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

    @RoleManager(["manager,employee,guest,client"])
    @PostMapping
    fun addUser(@RequestBody user: UserInputDto): ResponseEntity<UserOutputDto> {
        return try {
            val response = userServices.addUser(user)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            println("Exception = $e")
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["manager,employee,client"])
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Boolean>{
        return try {
            val response = userServices.deleteUser(id)
            ResponseEntity
                .status(201)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }

    }
    @RoleManager(["manager,employee,client"])
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserOutputDto> {
        return try {
            val response = userServices.getUserById(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["manager,employee,client"])
    @GetMapping("/{id}/role")
    fun getRole(@PathVariable id: Int): ResponseEntity<String?> {
        return try {
            val response = userServices.getRole(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["manager,employee"])
    @PutMapping("/{id}/role")
    fun changeRole(@PathVariable id: Int, @RequestBody roleName: String):  ResponseEntity<String> {
        return try {
            val json = Json.parseToJsonElement(roleName)
            val request = json.jsonObject["roleName"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            val response = userServices.changeRole(id, request)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["manager,employee"])
    @PutMapping("/{id}/availability")
    fun changeAvailability(@PathVariable id: Int, @RequestBody availability: String): ResponseEntity<String> {
        return try {
            val json = Json.parseToJsonElement(availability)
            val request = json.jsonObject["availability"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            val response = userServices.changeAvailability(request, id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["manager,employee,client"])
    @PutMapping("/{id}/password")
    fun changePassword(@PathVariable id: Int, @RequestBody password: String): ResponseEntity<String> {
        return try {
            val json = Json.parseToJsonElement(password)
            val request = json.jsonObject["password"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            val response = userServices.changePassword(request, id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    /*@PostMapping("/{id}/appointment")
    fun scheduleAnAppointment(@PathVariable id:Int, @RequestBody appointment: AppointmentInputDto): ResponseEntity<AppointmentOutputDto>{
        return try {
            val response = userServices.scheduleAnAppointment(id,appointment)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }*/


    @RoleManager(["manager"])
    @GetMapping("/employee")
    fun addEmployee(@RequestBody employee: UserInputDto): ResponseEntity<UserOutputDto> {
        return try {
            val response = userServices.addEmployee(employee)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["manager,employee,client"])
    @GetMapping("/log")
    fun getUserByEmailPass(@RequestBody password: String,@RequestBody email: String): ResponseEntity<UserOutputDto> {
        return try {
            val jsonPass = Json.parseToJsonElement(password)
            val requestPass = jsonPass.jsonObject["password"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            val jsonEmail = Json.parseToJsonElement(email)
            val requestEmail = jsonEmail.jsonObject["email"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            val response = userServices.getUsersByEmailAndPass(requestEmail,requestPass)

            println(response)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            println("here$e")
            ResponseEntity
                .status(400)
                .body(null)
        }
    }
}


