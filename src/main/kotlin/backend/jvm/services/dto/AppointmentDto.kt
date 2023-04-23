package backend.jvm.services.dto

import backend.jvm.model.Appointment
import java.sql.Date
import java.sql.Time


data class AppointmentInputDto(val appHour: String,
                               val appDate: String,
                               val schedule: Int,
                               val user: Int?,
                               val service: Int
)

data class AppointmentOutputDto(val id: Int,
                                val appHour: Time,
                                val appDate: Date,
                                val schedule: Int,
                                val user: Int?,
                                val service: Int
){
    constructor(appointment: Appointment) : this(
        id = appointment.id,
        appHour = appointment.appHour,
        appDate = appointment.appDate,
        schedule = appointment.schedule.id,
        user = appointment.userDB?.id,
        service = appointment.service.id
    )
}