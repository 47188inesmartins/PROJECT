package backend.jvm.controllers

import backend.jvm.services.AppointmentServices
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.utils.RoleManager
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping(("/company/{cid}/appointment"))
class AppointmentController {

    @Autowired
    lateinit var appointmentServices: AppointmentServices

    @RoleManager(["MANAGER", "CLIENT", "EMPLOYEE"])
    @PostMapping("")
    fun addAppointment(@RequestBody appointment: AppointmentInputDto, @PathVariable cid: Int): ResponseEntity<AppointmentOutputDto> {
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            val response = bearerToken?.let { appointmentServices.addAppointment(appointment, cid, it) }
            ResponseEntity.status(201)
                .body(response)
        }catch (e: Exception) {
            when(e) {
                is ScheduleNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found", e)
                is UserNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e)
                is ServiceNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found", e)
                else ->  throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }


    @RoleManager(["CLIENT","MANAGER","EMPLOYEE"])
    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Int, @PathVariable cid: Int): ResponseEntity<String> {
        return try {
            println(id)
            appointmentServices.deleteAppointment(id)
            ResponseEntity.status(200)
                .body("Appointment deleted!")
        } catch (e: Exception) {
            when(e) {
                is AppointmentNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not foun", e)
                is InvalidAppointment -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid appointment!", e)
                is EmployeeNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "No employee found", e)
                else ->  throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }


    @RoleManager(["MANAGER", "EMPLOYEE", "CLIENT"])
    @GetMapping("/{id}")
    fun getAppointment(@PathVariable id: Int, @PathVariable cid: String): ResponseEntity<AppointmentOutputDto>{
        return try {
           TODO()
            // val response = appointmentServices.getAppointment(id)
           // ResponseEntity.status(200)                .body(response)

        }catch (e: Exception) {
            when(e) {
                is AppointmentNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found!", e)
                else ->  throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }


    @RoleManager(["MANAGER", "EMPLOYEE", "CLIENT"])
    @GetMapping
    fun getAppointmentsByDateAndHour(@RequestParam("schedule_id") schedule_id: Int, @RequestParam("hour") hour: String, @RequestParam("date") date: String,
                                     @PathVariable cid: String
    ): ResponseEntity<List<AppointmentOutputDto>>{
        return try {
            val response = appointmentServices.getAppointmentByDateAndHour(schedule_id, hour, date)

            ResponseEntity.status(200)
                .body(response)
        } catch (e: Exception) {
            when(e) {
                is EmptyAppointments -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "No appointments found", e)
                else ->  throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }


    @RoleManager(["MANAGER", "EMPLOYEE", "CLIENT"])
    @GetMapping("/services/availability")
    fun getAvailableServices(@RequestParam("hour_begin") hourBegin :String, @RequestParam("date") date :String,
                             @PathVariable cid: Int
    ) :ResponseEntity<List<Pair<ServiceOutputDto, List<UserOutputDto>>>>{
        return try{
           val response = appointmentServices.getAvailableServicesByAppointment(hourBegin, date, cid)
            ResponseEntity.status(200) .body(response)
        }catch (e: Exception){
            when(e) {
                is EmptyAppointments -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "No appointments found", e)
                else ->  throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }

}