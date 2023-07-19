package backend.jvm.model.vacation

import backend.jvm.model.schedule.ScheduleEntity
import java.sql.Date

data class VacationInputDto(
    val dateBegin: String,
    val dateEnd: String,
   // val schedule: Int
){
    fun mapToVacationDb(dto: VacationInputDto, schedule: ScheduleEntity): VacationEntity {
        val begin = Date.valueOf(dto.dateBegin)?: throw Exception("invalid date")
        val end = Date.valueOf(dto.dateEnd) ?: throw Exception("invalid date")
        return VacationEntity(
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
    constructor(dto: VacationEntity):this(
        dto.id,
        dto.date_begin.toString(),
        dto.date_end.toString(),
        dto.schedule.id
    )
}