package backend.jvm.controllers

import backend.jvm.model.ServiceDB
import backend.jvm.services.ServServices
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.utils.RoleManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.util.*


@RestController
@RequestMapping("/api/service")
class ServiceController {

    @Autowired
    lateinit var servServices: ServServices

    @RoleManager(["MANAGER","EMPLOYEE"])
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


 /*   @RoleManager(["MANAGER", "EMPLOYEE"])
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
*/
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





