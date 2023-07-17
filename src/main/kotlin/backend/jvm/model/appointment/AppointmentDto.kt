package backend.jvm.model.appointment

import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.user.UserEntity
import java.sql.Date
import java.sql.Time

data class AppointmentInputDto(
    val appHour: String,
    val appDate: String,
    val user: Int?,
    val service: Int
) {
    fun mapToAppointmentDb(date:Date,dto: AppointmentInputDto, schedule: ScheduleEntity, user: List<UserEntity?>, service: ServiceEntity): AppointmentEntity {
        val ah = Time.valueOf(dto.appHour.plus(":00"))?: throw Exception("invalid hour")
        return AppointmentEntity(
            ah,
            date,
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
    val user:   List<Int?>?,
    val service: Int
){
    constructor(appointmentEntity: AppointmentEntity) : this(
        id = appointmentEntity.id,
        appHour = appointmentEntity.appHour,
        appDate = appointmentEntity.appDate,
        schedule = appointmentEntity.schedule.id,
        user = appointmentEntity.user?.map { it?.id },
        service = appointmentEntity.service.id
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

data class AppointmentInfoEmployeeEnd(
    val id: Int,
    val appBeginHour: String,
    val appEndHour:String,
    val appDate: Date,
    val employee: String
)

