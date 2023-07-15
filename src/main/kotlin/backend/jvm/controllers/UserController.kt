package backend.jvm.controllers

import backend.jvm.model.appointment.AppointmentsUserInfo
import backend.jvm.model.user.*
import backend.jvm.services.EmailSenderService
import backend.jvm.services.UserServices
import backend.jvm.services.dto.*
import backend.jvm.utils.RoleManager
import backend.jvm.utils.UserRoles
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
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userServices: UserServices

    @Autowired
    lateinit var emailSenderService: EmailSenderService

    @PostMapping
    fun signup(@RequestBody user: UserInputDto, response: HttpServletResponse): ResponseEntity<CreatedUserOutput> {
        return try {
            val resp = userServices.addUser(user)
            emailSenderService.sendValidationEmail(user.email)
            ResponseEntity
                    .status(201)
                    .body(resp)
        } catch (e: Exception) {
            when(e){
                is InvalidEmail -> throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Email",e)
                is PasswordInsecure -> throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Insecure Password",e)
                else -> throw ResponseStatusException(HttpStatus.CONFLICT, "Email already exits",e)
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

    @GetMapping("/info")
    fun getUser(): ResponseEntity<UserOutputDto> {
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found",Exception("User not found"))
            val response = userServices.getUserById(bearerToken)
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
    fun login(@RequestBody credentials: UserCredentials, response: HttpServletResponse): ResponseEntity<Pair<String?, String>> {
        return try {
            val user = userServices.getUsersByEmailAndPassword(credentials.email,credentials.password)
            val cookieToken = ResponseCookie
                .from("token", user.first)
                .maxAge(7 * 24 * 60 * 60 )
                .path("/")
                .httpOnly(true)
                .secure(false)
                .build()

            response.addHeader(HttpHeaders.SET_COOKIE, cookieToken.toString())

            ResponseEntity
                .status(200)
                .body(user)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password invalid", e)
        }
    }

    @GetMapping("/check-session")
    fun check(request:HttpServletRequest): ResponseEntity<Pair<String?, List<CompanyRole>>> {
        try {
             val cookies = request.cookies[1]
             val roles = userServices.getRolesByToken(cookies.value)
             val body = if(cookies == null)
                 Pair(null, listOf(CompanyRole()))
             else if(roles.isEmpty())
                 Pair(cookies.value, listOf(CompanyRole(role = UserRoles.CLIENT.name)))
             else Pair(cookies.value, roles)

             return ResponseEntity
                 .status(200)
                 .body(body)
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
    @GetMapping("/appointments")
    fun getAllAppointments(): ResponseEntity<AppointmentsUserInfo> {
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            val response = bearerToken?.let { userServices.getAllAppointmentsByUser(it) }
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: InvalidCredentials) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not appointment", e)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE"])
    @GetMapping("company/{cid}/receive-money")
    fun getEmployeeEarnedMoney(@RequestParam dateBegin: String, @RequestParam dateEnd: String,
                               @PathVariable cid: Int
    ): ResponseEntity<ReceiveMoney>{
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            val res = bearerToken?.let { userServices.getEarnedMoneyEmployee(it,dateBegin,dateEnd,cid) }
            val response = ReceiveMoney("$res €")
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: InvalidCredentials) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not appointment", e)
        }
    }

  //  @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @PostMapping("{id}/profile-pic")
    fun uploadProfilePic(@PathVariable id: Int,
                          @RequestBody file: MultipartFile
    ): ResponseEntity<String> {
        return try {
            userServices.updateUserProfilePicture(id,file)
            ResponseEntity
                .status(200)
                .body("Upload done")
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @PutMapping("/validate")
    fun validateAccount(@RequestParam email: String): ResponseEntity<String> {
        return try {
            userServices.validateAccount(email)
            ResponseEntity
                .status(200)
                .body("Account has been validated")
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }
}


