package backend.jvm.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getCurrentDate(): String{
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}

fun getCurrentTime(): String{
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return currentTime.format(formatter)
}