package backend.jvm.services.dto

import backend.jvm.model.*
import backend.jvm.utils.errorHandling.AlreadyEmployee
import java.sql.Date
import java.sql.Time


data class AppointmentInputDto(
    val appHour: String,
    val appDate: String,
    val schedule: Int,
    val user: List<Int>?,
    val service: Int
) {
    fun mapToAppointmentDb(dto: AppointmentInputDto, schedule: Schedule, user: List<UserDB>?, service: ServiceDB): Appointment {
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
    val user:   List<Int>?,
    val service: Int
){
    constructor(appointment: Appointment) : this(
        id = appointment.id,
        appHour = appointment.appHour,
        appDate = appointment.appDate,
        schedule = appointment.schedule.id,
        user = appointment.usersDB?.map { it.id },
        service = appointment.service.id
    )
}

data class AppointmentInfo(
    val id: Int,
    val appHour: String,
    val appDate: Date,
    val companyName: String,
    val employee: String
)
data class AppointmentsUserInfo(
    val futureAppointments: List<AppointmentInfo> = emptyList(),
    val passedAppointments: List<AppointmentInfo> = emptyList()
)

