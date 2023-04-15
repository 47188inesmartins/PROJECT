package backend.jvm.controllers

import backend.jvm.controllers.json.ServicesResponse
import backend.jvm.model.ServiceDB
import backend.jvm.services.ServServices
import backend.jvm.services.dto.ServiceInputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.util.*


@RestController
@RequestMapping("/service")
class ServiceController {

    @Autowired
    lateinit var servServices: ServServices

    @PostMapping
    fun addService(@RequestBody service: ServiceInputDto): ResponseEntity<ServicesResponse> {
        return try{
            val response = mapToResponse(servServices.addService(service))
            ResponseEntity.status(201)
                .body(response)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}")
    fun getService(@PathVariable id: Int): ResponseEntity<ServicesResponse> {
       return try {
           TODO()
        //   val response = mapToResponse(servServices.addService(servServices.getServiceById(id)))
          /* ResponseEntity.status(200)
               .body(response)*/
       }catch(e: Exception){
           ResponseEntity.status(400)
               .body(null)
       }
    }

    @GetMapping("/{id}/employee")
    fun getEmployeeForServices(@PathVariable id: Int): ResponseEntity<ServicesResponse> {
        return try {
            TODO()
          /*  val response = mapToResponse(servServices.addService(servServices.getServiceById(id)))
            ResponseEntity.status(200)
                .body(response)*/
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

    @PutMapping("/{id}/apointment/{idA}/avaibility")
    fun updateAvailability(@PathVariable id: Int,@PathVariable idA: Int, @RequestParam price: String): ResponseEntity<String> {
        return try {
            val response = servServices.updateAvailability(id,idA, price)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @PutMapping("/{id}/duration")
    fun updateDuration(@PathVariable id: Int, @RequestParam duration: String): ResponseEntity<Duration>{
        return try {
            val response = servServices.updateDuration(id, duration)
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

    @PutMapping("/{id}/appointment/{idA}/availability")
    fun updateMaxNumber(@PathVariable id: Int,@PathVariable idA: Int, @RequestBody availability: String): ResponseEntity<String> {
        return try {
            val response = servServices.updateAvailability(id,idA,availability)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @DeleteMapping
    fun deleteServices(@RequestBody serviceDB: ServiceDB): ResponseEntity<String> {
        return try {
             servServices.delete(serviceDB)
            ResponseEntity.status(200)
                .body("Service was deleted")
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    fun mapToResponse(serviceDB: ServiceDB): ServicesResponse {
        val listApp = serviceDB.appointment?.map { it.id }
        val dayApp = serviceDB.user?.map { it.id!! }
        val userApp = serviceDB.day?.map { it.id }

        return ServicesResponse(serviceDB.id,serviceDB.name,serviceDB.duration,serviceDB.numberMax,serviceDB.price,
            serviceDB.companyId.id, listApp!!, dayApp!!, userApp!!)

    }
}





