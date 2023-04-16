package backend.jvm.controllers

import backend.jvm.services.UnavailabilityServices
import backend.jvm.services.dto.UnavailabilityInputDto
import backend.jvm.services.dto.UnavailabilityOutputDto
import org.apache.catalina.connector.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/unavailability")
class UnavailabilityController {

    @Autowired
    lateinit var unavailabilityServices: UnavailabilityServices

    @PostMapping
    fun addUserUnavailability(@RequestBody unavailability: UnavailabilityInputDto): ResponseEntity<UnavailabilityOutputDto> {
        return try {
            val response = unavailabilityServices.addUnavailability(unavailability)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}/user")
    fun getUnavailabilityByUser(@PathVariable id: Int): ResponseEntity<UnavailabilityOutputDto> {
        return try {
            val response = unavailabilityServices.getUnavailabilityByUser(id)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUnavailability(@PathVariable id: Int): ResponseEntity<String> {
        return try {
            val response = unavailabilityServices.deleteUnavailability(id)
            ResponseEntity
                .status(201)
                .body("Deleted")
        } catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body("Error:${e}")
        }
    }
}