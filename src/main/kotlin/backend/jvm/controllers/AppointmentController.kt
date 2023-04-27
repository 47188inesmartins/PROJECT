package backend.jvm.controllers

import backend.jvm.services.AppointmentServices
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.utils.RoleManager
import org.springframework.beans.factory.annotation.Autowired
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


@RestController
@RequestMapping(("/appointment"))
class AppointmentController {

    @Autowired
    lateinit var appointmentServices: AppointmentServices

    @RoleManager(["MANAGER","CLIENT"])
    @ResponseBody
    @PostMapping
    fun addAppointment(@RequestBody appointment: AppointmentInputDto): ResponseEntity<AppointmentOutputDto> {
        return try {
            val response = appointmentServices.addAppointment(appointment)
            ResponseEntity.status(201)
                .body(response)
        }catch (e: Exception) {
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @RoleManager(["manager"])
    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Int){
        appointmentServices.deleteAppointment(id)
    }
    @RoleManager(["manager,employee,client"])
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

    /*@GetMapping("/{id}/services")
    fun getServices(@PathVariable id: Int): ResponseEntity<List<Int>>{
        return try {
            val response = appointmentServices.(id)
            ResponseEntity.status(200)
                .body(response)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }*/
    @RoleManager(["manager,employee,client"])
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

    /*@GetMapping("/services")
    fun getServicesByDateAndHour(@RequestParam("date") date: String, @RequestParam("hour") hour: String):ResponseEntity<Int>{
        return try{
            val response = appointmentServices.getNumberOfServicesByDateAndHour(service_id, date, hour)
            ResponseEntity.status(200)
                .body(response)
        }catch (e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }

    }*/
    @RoleManager(["manager,employee,client"])
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




    /*@GetMapping("/client")
    fun getAppointmentByClientAndDateAndTime(cid: Client, date: Date, time: Time): Appointment {
        return appointmentServices.findAppClientByDateAndHour(cid, date, time)
    }

    @GetMapping("/schedule")
    fun getAppointmentByScheduleAndDateAndTime(sid: Schedule, date: Date, time: Time): Appointment {
        return appointmentServices.findAllByAppHourAndSidAndAppDate(time, sid, date)
    }

    @PutMapping("/{id}/scheduled")
    fun changeNumberOfScheduledPeople(@PathVariable id: Int, @RequestParam number: Int): Appointment{
        return appointmentServices.editNumberAppPeople(id, number)
    }

    @PutMapping("/{id}/availability")
    fun changeAvailability(@PathVariable id: Int, @RequestBody availability: String): Appointment {
        return appointmentServices.editAvailability(id, availability)
    }
    */



}