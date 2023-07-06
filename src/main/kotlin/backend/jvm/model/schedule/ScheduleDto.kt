package backend.jvm.model.schedule

import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.day.DayEntity
import backend.jvm.model.vacation.VacationEntity


data class ScheduleInputDto(
    val companyId: Int,
    val appointment: List<Int>?,
    val day: List<Int>?,
    val vacation: List<Int>?
){
    fun mapToSchedule(companyEntity: CompanyEntity, appointmentEntity: List<AppointmentEntity>?, dayEntity: List<DayEntity>?, vacationEntity: List<VacationEntity>? ): ScheduleEntity =
        ScheduleEntity(
                companyEntity,
                appointmentEntity,
                dayEntity,
                vacationEntity,
                null
        )
}

data class ScheduleOutputDto(
    val id: Int,
    val companyId: Int,
    val appointment: List<Int>?,
    val day: List<Int>?,
    val vacation: List<Int>?
){
    constructor( scheduleDb: ScheduleEntity):this(
        id = scheduleDb.id,
        companyId = scheduleDb.company.id,
        appointment = scheduleDb.appointment?.map { it.id },
        day = scheduleDb.day?.map { it.id },
        vacation = scheduleDb.vacation?.map { it.id }
    )
}
