package backend.domain.DB

data class CalendarDB(
    val id : Int,
    val cid : Int,
    val weekDay : String,
    val calendarState : String
)