package backend.jvm.services.dto

import backend.jvm.model.Schedule
import backend.jvm.model.Vacation
import java.sql.Date

data class VacationInputDto(
    val dateBegin: String,
    val dateEnd: String,
   // val schedule: Int
){
    fun mapToVacationDb(dto: VacationInputDto, schedule: Schedule): Vacation {
        val begin = Date.valueOf(dto.dateBegin)?: throw Exception("invalid date")
        val end = Date.valueOf(dto.dateEnd) ?: throw Exception("invalid date")
        return Vacation(
            begin,
            end,
            schedule
        )
    }
}

data class VacationOutputDto(
    val id: Int,
    val dateBegin: String,
    val dateEnd: String,
    val schedule: Int?
){
    constructor(dto: Vacation):this(
        dto.id,
        dto.dateBegin.toString(),
        dto.dateEnd.toString(),
        dto.schedule.id
    )
}