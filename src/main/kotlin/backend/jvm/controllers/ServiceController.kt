package backend.jvm.controllers

import backend.jvm.model.Services
import backend.jvm.services.ServServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.sql.Time


@RestController
@RequestMapping("/service")
class ServiceController {

    @Autowired
    lateinit var servServices: ServServices

    @PostMapping
    fun addService(@RequestBody services: Services): Services {
        return servServices.addService(services)
    }

    @GetMapping("/{id}")
    fun getSchedule(@PathVariable id: Int): Services {
        return servServices.getServiceById(id)
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





