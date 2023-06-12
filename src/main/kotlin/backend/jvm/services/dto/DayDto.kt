package backend.jvm.services.dto

import backend.jvm.model.Day
import backend.jvm.model.Schedule
import backend.jvm.model.ServiceDB
import java.sql.Time

data class DayInputDto(
    val beginHour: String,
    val endHour: String,
    val intervalBegin: String?,
    val intervalEnd: String?,
    val weekDays: String,
    val schedule: Int?,
    val service: Int?,
) {
    fun mapToDayDb(dto: DayInputDto,schedule: Schedule?, service: ServiceDB?): Day {
        val begin = Time.valueOf(dto.beginHour.plus(":00"))?: throw Exception("invalid hour")
        val end = Time.valueOf(dto.endHour.plus(":00")) ?: throw Exception("invalid hour")
        val intervalEnd = if(dto.intervalEnd != null) Time.valueOf(dto.intervalEnd.plus(":00")) else null
        val intervalBegin = if(dto.intervalBegin != null) Time.valueOf(dto.intervalBegin.plus(":00")) else null

        if(intervalEnd != null && intervalBegin != null ) {
            if (begin.after(intervalBegin)) throw Exception("invalid hours")
            if (end.before(intervalEnd)) throw Exception("invalid hours")
        }

        return Day(
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
    val service: Int?
) {
        constructor(day: Day):this(
            day.id,
            day.beginHour.toString(),
            day.endHour.toString(),
            day.intervalBegin.toString(),
            day.intervalEnd.toString(),
            day.weekDays,
            day.schedule?.id,
            day.service?.id
        )
}



