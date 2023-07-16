package backend.jvm.controllers

import backend.jvm.services.UnavailabilityServices
import backend.jvm.services.dto.UnavailabilityInputDto
import backend.jvm.services.dto.UnavailabilityOutputDto
import backend.jvm.utils.RoleManager
import org.apache.catalina.connector.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


@RestController
@RequestMapping("unavailability")
class UnavailabilityController {

    @Autowired
    lateinit var unavailabilityServices: UnavailabilityServices

    @RoleManager(["MANAGER","EMPLOYEE"])
    @PostMapping("/company/{cid}")
    fun addUserUnavailability(@RequestBody unavailability: UnavailabilityInputDto, @PathVariable cid: String): ResponseEntity<UnavailabilityOutputDto> {
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            val response = unavailabilityServices.addUnavailability(bearerToken,unavailability)
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