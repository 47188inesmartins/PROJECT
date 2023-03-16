package backend.domain.DB

data class AppointmentDB (
    val id : Int,
    val numberAppPeople : Int,
    val appHour : String,
    val availability : String,
    val sid : Int,
    val cid : Int,
)