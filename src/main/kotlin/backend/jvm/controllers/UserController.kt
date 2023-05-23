package backend.jvm.controllers

import backend.jvm.services.UserServices
import backend.jvm.services.dto.*
import backend.jvm.utils.RoleManager
import backend.jvm.utils.errorHandling.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userServices: UserServices

    @PostMapping
    fun addUser(@RequestBody user: UserInputDto): ResponseEntity<CreatedUserOutput> {
        return try {
            val response = userServices.addUser(user)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            when(e){
                is InvalidEmail -> throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Email",e)
                is PasswordInsecure -> throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Insecure Password",e)
                else -> throw ResponseStatusException(HttpStatus.CONFLICT,"Email already exits",e)
            }
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Boolean>{
        return try {
            val response = userServices.deleteUser(id)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,"User not deleted",e)
        }
    }

   // @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserOutputDto> {
        return try {
            val response = userServices.getUserById(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: UserNotFound) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @GetMapping("/{id}/role")
    fun getRole(@PathVariable id: Int): ResponseEntity<String?> {
        return try {
            val response = userServices.getRole(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: UserNotFound) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
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
            throw  ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid user",e)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @PutMapping("/{id}/password")
    fun changePassword(@PathVariable id: Int, @RequestBody password: String): ResponseEntity<String> {
        return try {
            val json = Json.parseToJsonElement(password)
            val request = json.jsonObject["password"]?.jsonPrimitive?.content
                ?: throw InvalidPassword()

            val response = userServices.changePassword(request, id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            throw  ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid user",e)
        }
    }

    @RoleManager(["MANAGER"])
    @PostMapping("/company/{cid}/employee")
    fun addEmployee(@PathVariable cid: Int,@RequestBody email: String): ResponseEntity<CreatedUserOutput> {
        return try {
            val json = Json.parseToJsonElement(email)
            val request = json.jsonObject["email"]?.jsonPrimitive?.content
                ?: throw InvalidCredentials()
            val response = userServices.addEmployee(cid,request)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception) {
            println(e)
            when(e){
                is UserNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e)
                is CompanyNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e)
                is AlreadyEmployee -> throw ResponseStatusException(HttpStatus.CONFLICT, "Already Employee", e)
                is AlreadyCompanyManager -> throw ResponseStatusException(HttpStatus.CONFLICT, "Already Company Manager", e)
                else -> throw  ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid user",e)
            }
        }
    }


    @PostMapping("/login")
    fun login(@RequestBody credentials: UserCredentials): ResponseEntity<UUID> {
        return try {

            /*

            val jsonPass = Json.parseToJsonElement(credentials.password)
            val requestPass = jsonPass.jsonObject["password"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            val jsonEmail = Json.parseToJsonElement(credentials.email)
            val requestEmail = jsonEmail.jsonObject["email"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            */

            val response = userServices.getUsersByEmailAndPass(credentials.email,credentials.password)

            println(response)
            ResponseEntity
                .status(200)
                .body(response.token)
        }catch (e: InvalidCredentials) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password invalid", e)
        }
    }

    @RoleManager(["CLIENT"])
    @GetMapping("/{id}/appointments")
    fun getAllEmployee(@PathVariable id: Int): ResponseEntity<List<AppointmentOutputDto>> {
        return try {
            val response = userServices.getAllAppointments(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: InvalidCredentials) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not appointment", e)
        }
    }
}


