package backend.jvm.controllers

import backend.jvm.model.Appointment
import backend.jvm.services.AppointmentServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(("/appointment"))
class AppointmentController {

    @Autowired
    lateinit var appointmentServices: AppointmentServices

    @PostMapping
    fun addAppointment(appointment: Appointment): Appointment{
        return appointmentServices.addAppointment(appointment)
    }


}