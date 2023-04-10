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
            val service = servServices.addService(services)
            val listApp = service.appointment?.map {
                it.id
            }

            val dayApp = service.user?.map {
                it.id!!
            }

            val userApp = service.day?.map {
                it.id!!
            }
            val response = ServicesResponse(service.id,service.name,service.duration,service.numberMax,service.price, service.cid?.id!!, listApp!!, dayApp!!, userApp!!)
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
           val service = servServices.getServiceById(id)

           val listApp = service.appointment?.map {
               it.id
           }

           val dayApp = service.user?.map {
               it.id!!
           }

           val userApp = service.day?.map {
               it.id!!
           }

           val response = ServicesResponse(
               service.id,
               service.name,
               service.duration,
               service.numberMax,
               service.price,
               service.cid?.id!!,
               listApp!!,
               userApp!!,
               dayApp!!
           )
           ResponseEntity.status(200)
               .body(response)
       }catch(e: Exception){
           ResponseEntity.status(400)
               .body(null)
       }
    }

    @GetMapping("/employee/{id}")
    fun getEmployeeForServices(@PathVariable id: Int): Services {
        return servServices.getEmployeeForService(id)
    }

    @PutMapping("/price/{id}")
    fun updatePrice(@PathVariable id: Int, @RequestParam price: Double): Services {
        return servServices.updatePrice(id, price)
    }

    @PutMapping("/duration/{id}")
    fun updateDuration(@PathVariable id: Int, @RequestParam duration: String): Services {
        return servServices.updateDuration(id, duration)
    }

    @PutMapping("/people/{id}")
    fun updateDuration(@PathVariable id: Int, @RequestParam numberPeople: Int): Services {
        return servServices.updateMaxNumber(id, numberPeople)
    }

    @DeleteMapping
    fun deleteServices(@RequestBody service: Services) {
        servServices.delete(service)
    }
}





