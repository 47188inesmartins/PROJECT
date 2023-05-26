package backend.jvm.utils

import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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