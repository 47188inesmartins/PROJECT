package backend.jvm.services.dto

import backend.jvm.model.Day
import backend.jvm.model.Schedule
import backend.jvm.model.ServiceDB
import backend.jvm.utils.errorHandling.InvalidOpenDay
import java.sql.Time

data class DayInputDto(
    val beginHour: String,
    val endHour: String,
    val intervalBegin: String,
    val intervalEnd: String,
    val weekDays: String,
    val schedule: Int?,
    val service: Int?,
) {
    fun mapToDayDb(dto: DayInputDto,schedule: Schedule?, service: ServiceDB?): Day {
        val begin = Time.valueOf(dto.beginHour)?: throw Exception("invalid hour")
        val end = Time.valueOf(dto.endHour) ?: throw Exception("invalid hour")
        val intervalEnd = Time.valueOf(dto.intervalEnd) ?: throw Exception("invalid interval")
        val intervalBegin = Time.valueOf(dto.intervalBegin) ?: throw Exception("invalid interval")

        if(begin.after(intervalBegin)) throw Exception("invalid hours")
        if(end.before(intervalEnd)) throw Exception("invalid hours")

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



