package backend.jvm.controllers

import backend.jvm.model.Appointment
import backend.jvm.services.AppointmentServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(("/appointment"))
class AppointmentController {

    @Autowired
    lateinit var appointmentServices: AppointmentServices

    @PostMapping
    fun addAppointment(@RequestBody appointment: Appointment): Appointment{
        return appointmentServices.addAppointment(appointment)
    }

    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Int): Boolean {
        return appointmentServices.deleteAppointment(id)
    }

    @GetMapping("/{id}")
    fun getAppointment(@PathVariable id: Int): Appointment{
        return appointmentServices.getAppointment(id).get()
    }

    /*@GetMapping("/client")
    fun getAppointmentByClientAndDateAndTime(cid: Client, date: Date, time: Time): Appointment {
        return appointmentServices.findAppClientByDateAndHour(cid, date, time)
    }

    @GetMapping("/schedule")
    fun getAppointmentByScheduleAndDateAndTime(sid: Schedule, date: Date, time: Time): Appointment {
        return appointmentServices.findAllByAppHourAndSidAndAppDate(time, sid, date)
    }*/

    @PutMapping("/{id}/availability")
    fun changeAvailability(@PathVariable id: Int, @RequestParam availability: String): Appointment {
        return appointmentServices.editAvailability(id, availability)
    }

    @PutMapping("/{id}/scheduled")
    fun changeNumberOfScheduledPeople(@PathVariable id: Int, @RequestParam number: Int): Appointment{
        return appointmentServices.editNumberAppPeople(id, number)
    }
}