package backend.jvm.utils

import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

fun getCurrentDate(): Date {
    val actualDate = System.currentTimeMillis()
    return Date(actualDate)
}

fun getCurrentTime(): Time {
    val actualTime = System.currentTimeMillis()
    return Time(actualTime)
}

fun convertToHourFormat(hours: Time): String {
    val hoursSplitted = hours.toString().split(":")
    val hour = hoursSplitted[0].toInt()
    val minute = hoursSplitted[1]
    return "${hour}h$minute"
}


fun getLastDateIn30Days(date:Date): Date{
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DAY_OF_MONTH, -30)
    return Date(calendar.timeInMillis)
}