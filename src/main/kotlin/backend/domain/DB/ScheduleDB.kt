package backend.domain.DB

import java.sql.Time

data class ScheduleDB(
    val id : Int,
    val beginHour : Int,
    val endHour : Time,
    val shcInterval : Time,
    val duration : Time,
    val compId : Int
)