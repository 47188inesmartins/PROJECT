package backend.jvm.controllers

import backend.jvm.controllers.json.ServicesResponse
import backend.jvm.model.Services
import backend.jvm.services.ServServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/service")
class ServiceController {

    @Autowired
    lateinit var servServices: ServServices

    @PostMapping
    fun addService(@RequestBody services: Services): ResponseEntity<ServicesResponse> {
        return try{
            val response = mapToResponse(servServices.addService(services))

            ResponseEntity.status(201)
                .body(response)
        }catch (e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}")
    fun getService(@PathVariable id: Int): ResponseEntity<ServicesResponse> {
       return try {
           val response = mapToResponse(servServices.addService(servServices.getServiceById(id)))
           ResponseEntity.status(200)
               .body(response)
       }catch(e: Exception){
           ResponseEntity.status(400)
               .body(null)
       }
    }

    @GetMapping("/{id}/employee")
    fun getEmployeeForServices(@PathVariable id: Int): ResponseEntity<ServicesResponse> {
        return try {
            val response = mapToResponse(servServices.addService(servServices.getServiceById(id)))
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
            val response = servServices.updatePrice(id, price)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @PutMapping("/{id}/duration")
    fun updateDuration(@PathVariable id: Int, @RequestParam duration: String): ResponseEntity<ServicesResponse>{
        return try {
            val response = mapToResponse(servServices.updateDuration(id, duration))
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}/appointment/{idA}/available")
    fun verifyAvailability(@PathVariable id: Int, @PathVariable idA: Int): ResponseEntity<String>{
        return try {
            val response = servServices.verifyAvailability(id,idA)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }


    @PutMapping("/{id}/people")
    fun updateMaxNumber(@PathVariable id: Int, @RequestParam numberPeople: Int): ResponseEntity<ServicesResponse> {
        return try {
            val response = mapToResponse(servServices.updateMaxNumber(id, numberPeople))
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @DeleteMapping
    fun deleteServices(@RequestBody service: Services): ResponseEntity<String> {
        return try {
             servServices.delete(service)
            ResponseEntity.status(200)
                .body("Service was deleted")
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    fun mapToResponse(services: Services): ServicesResponse {
        val listApp = services.appointment?.map { it.id }
        val dayApp = services.user?.map { it.id!! }
        val userApp = services.day?.map { it.id }

        return ServicesResponse(services.id,services.name,services.duration,services.numberMax,services.price, services.cid?.id!!, listApp!!, dayApp!!, userApp!!)
    }
}





