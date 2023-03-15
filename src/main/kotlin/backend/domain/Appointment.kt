package backend.domain

data class Appointment(
    val appNumber: Int,
    val appHour : String,
    val availability : String,
    val sid : Int,
    val cid : Int
)