package backend.domain

import java.util.*

data class Client(
        val email:String,
        val password :String,
        val username :String,
        val clientName :String,
        val birthday : Date
)