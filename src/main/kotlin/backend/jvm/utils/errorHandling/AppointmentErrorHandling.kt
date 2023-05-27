package backend.jvm.utils.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Price must be greater then 0")
class Invalid:Exception("Price must be greater then 0")

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Appointment not found ")
class AppointmentNotFound : Exception("Appointment not found ")

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No appointments found")
class EmptyAppointments : Exception("No appointments found")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Appointment")
class InvalidAppointment : Exception("Invalid Appointment")

