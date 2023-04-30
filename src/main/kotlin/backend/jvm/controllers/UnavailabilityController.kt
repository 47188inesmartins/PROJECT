package backend.jvm.controllers

import backend.jvm.services.UnavailabilityServices
import backend.jvm.services.dto.UnavailabilityInputDto
import backend.jvm.services.dto.UnavailabilityOutputDto
import backend.jvm.utils.RoleManager
import org.apache.catalina.connector.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/unavailability")
class UnavailabilityController {

    @Autowired
    lateinit var unavailabilityServices: UnavailabilityServices

    @RoleManager(["MANAGER","EMPLOYEE"])
    @PostMapping
    fun addUserUnavailability(@RequestBody unavailability: UnavailabilityInputDto): ResponseEntity<UnavailabilityOutputDto> {
        return try {
            val response = unavailabilityServices.addUnavailability(unavailability)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            println(e.toString())
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE"])
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

    @RoleManager(["MANAGER","EMPLOYEE"])
    @DeleteMapping("/{id}")
    fun deleteUnavailability(@PathVariable id: Int): ResponseEntity<String> {
        return try {
            unavailabilityServices.deleteUnavailability(id)
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