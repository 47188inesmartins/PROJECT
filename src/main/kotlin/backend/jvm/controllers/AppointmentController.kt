package backend.jvm.controllers

import backend.jvm.controllers.json.AppointmentResponse
import backend.jvm.model.Appointment
import backend.jvm.services.AppointmentServices
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
import java.sql.Date
import java.sql.Time

@RestController
@RequestMapping(("/appointment"))
class AppointmentController {

    @Autowired
    lateinit var appointmentServices: AppointmentServices

    @ResponseBody
    @PostMapping
    fun addAppointment(@RequestBody appointment: Appointment): ResponseEntity<AppointmentResponse> {
        return try {
            val app = appointmentServices.addAppointment(appointment)
            val response = AppointmentResponse(app.id, app.appHour, app.appDate, app.sid.id, app.uid?.id)
            ResponseEntity.status(201)
                .body(response)

        }catch (e: Exception) {
            ResponseEntity.status(400)
                .body(null)
        }
    }


    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Int){
        appointmentServices.deleteAppointment(id)
    }

    @GetMapping("/{id}")
    fun getAppointment(@PathVariable id: Int): ResponseEntity<AppointmentResponse>{
        return try {
            val app = appointmentServices.getAppointment(id).get()
            val response = AppointmentResponse(app.id, app.appHour, app.appDate, app.sid.id, app.uid?.id)
            ResponseEntity.status(200)
                .body(response)

        }catch (e: Exception) {
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}/services")
    fun getServices(@PathVariable id: Int): ResponseEntity<List<Int>>{
        return try {
            val response = appointmentServices.getServices(id)
            ResponseEntity.status(200)
                .body(response)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping
    fun getAppointmentsByDateAndHour(@RequestParam("sid") sid: Int, @RequestParam("hour") hour: String, @RequestParam("date") date: String): ResponseEntity<List<AppointmentResponse>>{
        return try {
            val h = Time.valueOf(hour)
            val d = Date.valueOf(date)
            val app = appointmentServices.getAppointmentByDateAndHour(h, sid, d)
            app.forEach { println("app = " + it) }
            val response = app.map { AppointmentResponse(it.id, it.appHour, it.appDate, it.sid.id, it.uid?.id) }
            ResponseEntity.status(200)
                .body(response)

        }catch (e: Exception){
            println("error=$e")
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