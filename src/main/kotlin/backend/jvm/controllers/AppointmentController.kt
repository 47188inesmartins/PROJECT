package backend.jvm.controllers

import backend.jvm.services.AppointmentServices
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.utils.RoleManager
import backend.jvm.utils.errorHandling.ServiceNotFound
import backend.jvm.utils.errorHandling.UserNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping(("/appointment"))
class AppointmentController {

    @Autowired
    lateinit var appointmentServices: AppointmentServices

    @RoleManager(["MANAGER", "CLIENT"])
    @PostMapping
    fun addAppointment(@RequestBody appointment: AppointmentInputDto): ResponseEntity<AppointmentOutputDto> {
        return try {
            val response = appointmentServices.addAppointment(appointment)
            ResponseEntity.status(201)
                .body(response)
        }catch (e: Exception) {
            when(e) {
                is UserNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e)
                is ServiceNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found", e)
                else ->  throw ResponseStatusException(HttpStatus.BAD_REQUEST, "", e)
            }
        }
    }


    @RoleManager(["MANAGER"])
    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Int){
        appointmentServices.deleteAppointment(id)
    }


    @RoleManager(["MANAGER", "EMPLOYEE", "CLIENT"])
    @GetMapping("/{id}")
    fun getAppointment(@PathVariable id: Int): ResponseEntity<AppointmentOutputDto>{
        return try {
            val response = appointmentServices.getAppointment(id)
            ResponseEntity.status(200)
                .body(response)

        }catch (e: Exception) {
            ResponseEntity.status(400)
                .body(null)
        }
    }


    @RoleManager(["MANAGER", "EMPLOYEE", "CLIENT"])
    @GetMapping
    fun getAppointmentsByDateAndHour(@RequestParam("schedule_id") schedule_id: Int, @RequestParam("hour") hour: String, @RequestParam("date") date: String): ResponseEntity<List<AppointmentOutputDto>>{
        return try {
            val response = appointmentServices.getAppointmentByDateAndHour(schedule_id, hour, date)

            ResponseEntity.status(200)
                .body(response)

        }catch (e: Exception){
            println("error=$e")
            ResponseEntity.status(400)
                .body(null)
        }
    }


    @RoleManager(["MANAGER", "EMPLOYEE", "CLIENT"])
    @GetMapping("/services/availability")
    fun getAvailableServices(@RequestParam("hour_begin") hourBegin :String, @RequestParam("date") date :String, @RequestParam("companyId") companyId :Int ) :ResponseEntity<List<ServiceOutputDto>>{
        return try{
            val response = appointmentServices.getAvailableServicesByEmployees(hourBegin, date, companyId)
            ResponseEntity.status(200)
                .body(response)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }
}