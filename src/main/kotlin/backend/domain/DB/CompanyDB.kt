package backend.domain.DB

import java.util.*

data class CompanyDB(
    val id : Int,
    val token : UUID,
    val email : String,
    val password : String,
    val username : String,
    val compName : String,
    val compType : String,
    val description : String
)