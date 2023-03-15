package backend.domain

import java.sql.Time

data class Service(
    val serviceName :String,
    val duration :Time,
    val numberMax :Int,
    val cid :Int,
    val aid :Int
)