package backend.jvm.utils.time

import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.util.*

fun getDayOfWeek(date : Date): String{
    val utilDate = java.util.Date(date.time)
    val calendar = Calendar.getInstance()
    calendar.time = utilDate

    return when(calendar[Calendar.DAY_OF_WEEK]) {
        Calendar.SUNDAY -> "SUN"
        Calendar.MONDAY -> "MON"
        Calendar.TUESDAY -> "TUE"
        Calendar.WEDNESDAY -> "WED"
        Calendar.THURSDAY -> "THU"
        Calendar.FRIDAY -> "FRI"
        Calendar.SATURDAY -> "SAT"
        else -> "invalid data"
    }
}

fun getCurrentDate(): Date = Date.valueOf(LocalDate.now())


fun getCurrentHour(): Time {
    val currentTime = Calendar.getInstance().time
    return Time(currentTime.time)
}


fun addTimes(tempo1: Time, tempo2: Time): Time {
    val additionalTime = tempo2.time - tempo2.timezoneOffset * 60 * 1000
    return Time(tempo1.time + additionalTime)
}