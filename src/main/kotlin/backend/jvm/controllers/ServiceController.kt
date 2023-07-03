package backend.jvm.controllers

import backend.jvm.model.ServiceDB
import backend.jvm.services.ServServices
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.utils.RoleManager
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.Duration
import java.util.*


@RestController
@RequestMapping("/service")
class ServiceController {

    @Autowired
    lateinit var servServices: ServServices

    @RoleManager(["MANAGER","EMPLOYEE"])
    @PostMapping("/company/{cid}")
    fun addService(@RequestBody service: ServiceInputDto, @PathVariable cid: Int): ResponseEntity<ServiceOutputDto> {
        return try {
            val response = servServices.addService(service,cid)
            ResponseEntity.status(201)
                .body(response)
        }catch (e: Exception){
            when (e) {
                is EntityNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "entity does not exists", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
        }
    }

    @GetMapping("/{id}")
    fun getService(@PathVariable id: Int): ResponseEntity<ServiceOutputDto> {
       return try {
           val response = servServices.getServiceById(id)
           ResponseEntity.status(200)
               .body(response)
       }catch(e: Exception){
           ResponseEntity.status(400)
               .body(null)
       }
    }

    @GetMapping("/{id}/employee")
    fun getAvailableEmployeesForService (@PathVariable id: Int): ResponseEntity<ServiceOutputDto> {
        return try {
            val response = servServices.getServiceById(id)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }


    @PutMapping("/{id}/price")
    fun updatePrice(@PathVariable id: Int, @RequestParam price: Double): ResponseEntity<Long> {
        return try {
            val response = servServices.changePrice(id, price)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @PutMapping("/{id}/schedule")
    fun updateSchedule(@PathVariable id: Int, @RequestBody day: List<DayInputDto>) {
        try {
            val response = servServices.changeSchedule(id, day)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @RoleManager(["MANAGER", "EMPLOYEE"])
    @PutMapping("/{id}/duration")
    fun updateDuration(@PathVariable id: Int, @RequestParam duration: String): ResponseEntity<Duration>{
        return try {
            val response = servServices.changeDuration(id, duration)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @RoleManager(["MANAGER"])
    @DeleteMapping
    fun deleteServices(@RequestBody serviceDB: ServiceDB): ResponseEntity<String> {
        return try {
             servServices.deleteService(serviceDB)
             ResponseEntity.status(200)
                .body("Service was deleted")
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }
}





