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

    @RoleManager(["manager,employee"])
    @PostMapping
    fun addUserUnavailability(@RequestBody unavailability: UnavailabilityInputDto): ResponseEntity<UnavailabilityOutputDto> {
        return try {
            println("tas a gozar")
            println(""+unavailability.user+unavailability.dateBegin+unavailability.dateEnd+unavailability.hourBegin+unavailability.hourEnd)
            val response = unavailabilityServices.addUnavailability(unavailability)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            println("tas a gozar v2")
            println("no ctatchhhhhh"+unavailability.user+unavailability.dateBegin+unavailability.dateEnd+unavailability.hourBegin+unavailability.hourEnd)

            println(e.toString())
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["manager,employee"])
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

    @RoleManager(["manager,employee"])
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