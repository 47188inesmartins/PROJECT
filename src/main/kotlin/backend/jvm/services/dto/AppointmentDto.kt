package backend.jvm.services.dto

import backend.jvm.model.*
import java.sql.Date
import java.sql.Time


data class AppointmentInputDto(
    val appHour: String,
    val appDate: String,
    val schedule: Int,
    val user: Int?,
    val service: Int
) {
    fun mapToAppointmentDb(dto: AppointmentInputDto, schedule: Schedule, user: UserDB?, service: ServiceDB): Appointment {
        val ah = Time.valueOf(dto.appHour)?: throw Exception("invalid hour")
        val ad = Date.valueOf(dto.appDate)?: throw Exception("invalid date")
        return Appointment(
            ah,
            ad,
            schedule,
            user,
            service
        )
    }
}

data class AppointmentOutputDto(
    val id: Int,
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