package backend.jvm.controllers

import backend.jvm.services.UserServices
import backend.jvm.services.dto.*
import backend.jvm.utils.RoleManager
import backend.jvm.utils.errorHandling.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/api/user")
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

    @PostMapping("/login")
    fun login(@RequestBody credentials: UserCredentials,response: HttpServletResponse): ResponseEntity<UUID> {
        return try {
            val user = userServices.getUsersByEmailAndPassword(credentials.email,credentials.password)
            val cookieToken = ResponseCookie
                .from("token", user.token.toString())
                .maxAge(7 * 24 * 60 * 60 )
                .path("/")
                .httpOnly(true)
                .secure(false)
                .build()
            response.addHeader(HttpHeaders.SET_COOKIE, cookieToken.toString())
            ResponseEntity
                .status(200)
                .body(user.token)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password invalid", e)
        }
    }

    @GetMapping("/check-session")
    fun check(
              request:HttpServletRequest
    ): ResponseEntity<Pair<String, List<CompanyRole>?>> {
        try {
           //  response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            // println(token)
             val cookies = request.cookies.first()
             val t = cookies ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token")
             val roles = userServices.getRolesByToken(t.value)
             return ResponseEntity
                 .status(200)
                 .body(Pair(t.value, roles))
        }catch (e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token", e)
        }
    }



    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<String> {
        return try {
            val cookieToken = ResponseCookie
                .from("token", "")
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .build()
            response.addHeader(HttpHeaders.SET_COOKIE, cookieToken.toString())
            ResponseEntity
                .status(200)
                .body("Logout completed")
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password invalid", e)
        }
    }

    @RoleManager(["CLIENT"])
    @GetMapping("/{id}/appointments")
    fun getAllAppointments(@PathVariable id: Int): ResponseEntity<AppointmentsUserInfo> {
        return try {
            val response = userServices.getAllAppointmentsByUser(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: InvalidCredentials) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not appointment", e)
        }
    }
}


