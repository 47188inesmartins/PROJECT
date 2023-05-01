package backend.jvm.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
class HomeController {

    @GetMapping("/")
    fun home(): ResponseEntity<String> {
        return try {
            val response = "Welcome to your schedule application!"
            ResponseEntity
                .status(200)
                .body(response)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Application error", e)
        }
    }
}