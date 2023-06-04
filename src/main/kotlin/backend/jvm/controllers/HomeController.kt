package backend.jvm.controllers



import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
class HomeController {

    @GetMapping("/api", produces= [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun home(): ResponseEntity<String> {
        return try {
            val response = mapOf("message" to "Hello world")
            val objectMapper = ObjectMapper()
            val responseBody = objectMapper.writeValueAsString(response)!!
            ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(responseBody)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Application error", e)
        }
    }
}