package backend.domain.DB

import java.util.*

data class ClientDB(
    val id : Int,
    val token : UUID,
    val email : String,
    val password : String,
    val username : String,
    val cliName : String,
    val birthday : Date
)