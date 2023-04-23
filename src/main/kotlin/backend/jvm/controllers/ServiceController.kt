package backend.jvm.controllers

import backend.jvm.model.ServiceDB
import backend.jvm.services.ServServices
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
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
    fun addService(@RequestBody service: ServiceInputDto): ResponseEntity<ServiceOutputDto> {
        return try{
            val response = servServices.addService(service)
            ResponseEntity.status(201)
                .body(response)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
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

  /*  @GetMapping("/{id}/employee")
    fun getEmployeesForService(@PathVariable id: Int, @RequestParam hourBegin: String, hourEnd: String, date: String): ResponseEntity<List<UserOutputDto>> {
        return try {
            val response = servServices.getAvailableEmployees(id, hourBegin, hourEnd, date)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }*/



    @GetMapping("{id}/employees")
    fun getAvailableEmployees(@RequestParam("date") date: String, @RequestParam("beginHour") beginHour: String, @RequestParam("endHour") endHour: String,
                              @PathVariable id: Int
    ): ResponseEntity<List<UserOutputDto>>{
        println("id = $id")
        return try{
            val response = servServices.getAvailableEmployees(id, beginHour, endHour, date)
            ResponseEntity.status(200)
                .body(response)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400).body(null)
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

    /*   @PutMapping("/{id}/apointment/{idA}/avaibility")
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
    */

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

  /*  @GetMapping("/{id}/appointment/{idA}/available")
    fun verifyAvailability(@PathVariable id: Int, @PathVariable idA: Int): ResponseEntity<String>{
        return try {
            val response = servServices.verifyAvailability(id,idA)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }*/

   /* @PutMapping("/{id}/appointment/{idA}/availability")
    fun updateMaxNumber(@PathVariable id: Int,@PathVariable idA: Int, @RequestBody availability: String): ResponseEntity<String> {
        return try {
            val response = servServices.updateAvailability(id,idA,availability)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }*/

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

}





