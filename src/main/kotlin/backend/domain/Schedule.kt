package backend.domain

import java.sql.Time

data class Schedule(
    val beginHour : Time,
    val endHour : Time,
    val shcInterval : Time,
    val duration : Time,
    val compId : Int
)