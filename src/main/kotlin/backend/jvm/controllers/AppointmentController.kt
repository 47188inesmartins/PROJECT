package backend.jvm.controllers

import backend.jvm.model.Appointment
import backend.jvm.model.Client
import backend.jvm.model.Company
import backend.jvm.model.Schedule
import backend.jvm.services.AppointmentServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.sql.Time
import java.util.*

@RestController
@RequestMapping(("/appointment"))
class AppointmentController {

    @Autowired
    lateinit var appointmentServices: AppointmentServices

    @PostMapping
    fun addAppointment(appointment: Appointment): Appointment{
        return appointmentServices.addAppointment(appointment)
    }

    @DeleteMapping("/{id}")
    fun deleteAppointment(id: Int): Boolean {
        return appointmentServices.deleteAppointment(id)
    }

    @GetMapping("/{id}")
    fun getAppointment(id: Int): Appointment{
        return appointmentServices.getAppointment(id).get()
    }

    @GetMapping("/client")
    fun getAppointmentByClientAndDateAndTime(cid: Client, date: Date, time: Time): Appointment {
        return appointmentServices.findAppClientByDateAndHour(cid, date, time)
    }

    @GetMapping("/schedule")
    fun getAppointmentByScheduleAndDateAndTime(sid: Schedule, date: Date, time: Time): Appointment {
        return appointmentServices.findAllByAppHourAndSidAndAppDate(time, sid, date)
    }

    @PutMapping("/{id}/availability")
    fun changeAvailability(id: Int, @RequestParam availability: String): Appointment {
        return appointmentServices.editAvailability(id, availability)
    }

    @PutMapping("/{id}/scheduled")
    fun changeNumberOfScheduledPeople(id: Int, @RequestParam number: Int): Appointment{
        return appointmentServices.editNumberAppPeople(id, number)
    }
}