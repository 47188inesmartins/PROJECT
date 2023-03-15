package backend.domain.DB

import java.sql.Time

data class ServiceDB(
    val id : Int,
    val serviceName : String,
    val duration : Time,
    val numberMax : Int,
    val cid : Int,
    val aid : Int
)