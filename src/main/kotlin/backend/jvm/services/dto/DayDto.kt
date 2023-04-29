package backend.jvm.services.dto

import backend.jvm.model.Day
import backend.jvm.model.Schedule
import backend.jvm.model.ServiceDB
import jakarta.persistence.*
import java.sql.Time

data class DayInputDto(
    val beginHour: String,
    val endHour: String,
    val intervalE: String,
    val intervalB: String,
    val weekDays: String,
    val schedule: Int?,
    val service: Int?,
) {
    fun mapToDayDb(dto: DayInputDto,schedule: Schedule?, service: ServiceDB?): Day {
        val begin = Time.valueOf(dto.beginHour)?: throw Exception("invalid hour")
        val end = Time.valueOf(dto.endHour) ?: throw Exception("invalid hour")
        val intervalE = Time.valueOf(dto.intervalE) ?: throw Exception("invalid interval")
        val intervalB = Time.valueOf(dto.intervalB) ?: throw Exception("invalid interval")

        return Day(
            beginHour = begin,
            endHour = end,
            intervalBegin = intervalB,
            intervalEnd = intervalE ,
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
        constructor(day: Day):this(
            day.id,
            day.beginHour.toString(),
            day.endHour.toString(),
            day.intervalBegin.toString(),
            day.intervalEnd.toString(),
            day.weekDays,
            day.schedule?.id
            )
}



