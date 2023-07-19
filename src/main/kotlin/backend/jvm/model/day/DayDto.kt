package backend.jvm.model.day

import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.service.ServiceEntity
import java.sql.Time

data class DayInputDto(
    val beginHour: String,
    val endHour: String,
    val intervalBegin: String?,
    val intervalEnd: String?,
    val weekDays: String,
    val schedule: Int?,
) {
    fun mapToDayDb(dto: DayInputDto, schedule: ScheduleEntity?, service: List<ServiceEntity>?): DayEntity {
        val begin = Time.valueOf(dto.beginHour.plus(":00"))?: throw Exception("invalid hour")
        val end = Time.valueOf(dto.endHour.plus(":00")) ?: throw Exception("invalid hour")
        val intervalEnd = if(dto.intervalEnd != null && dto.intervalEnd != "") Time.valueOf(dto.intervalEnd.plus(":00")) else null
        val intervalBegin = if(dto.intervalBegin != null && dto.intervalBegin != "") Time.valueOf(dto.intervalBegin.plus(":00")) else null

        if(intervalEnd != null && intervalBegin != null ) {
            if (begin.after(intervalBegin)) throw Exception("invalid hours")
            if (end.before(intervalEnd)) throw Exception("invalid hours")
        }

        return DayEntity(
            beginHour = begin,
            endHour = end,
            intervalBegin = intervalBegin,
            intervalEnd = intervalEnd ,
            weekDays = dto.weekDays,
            schedule = schedule,
            service = service
        )
    }
}

data class DayOutputDto(
    val id: Int,
    val beginHour: String,
    val endHour: String,
    val intervalBegin: String,
    val intervalEnd: String,
    val weekDays: String,
    val schedule: Int?,
) {
        constructor(dayEntity: DayEntity):this(
            dayEntity.id,
            dayEntity.beginHour.toString(),
            dayEntity.endHour.toString(),
            dayEntity.intervalBegin.toString(),
            dayEntity.intervalEnd.toString(),
            dayEntity.weekDays,
            dayEntity.schedule?.id,
        )
}



