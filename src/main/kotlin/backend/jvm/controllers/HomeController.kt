package backend.jvm.controllers



import backend.jvm.services.UserServices
import backend.jvm.services.dto.CompanyOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/")
class HomeController {

    @Autowired
    lateinit var userServices: UserServices



    @GetMapping
    fun home(): ResponseEntity<List<CompanyOutputDto>> {
        return try{
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            val response = userServices.getPersonalizedCompanies(bearerToken)
            ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(response)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Application error", e)
        }
    }
}