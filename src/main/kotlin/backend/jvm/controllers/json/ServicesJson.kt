package backend.jvm.controllers.json

import kotlinx.serialization.Serializable
import java.sql.Time


@Serializable
data class ServicesResponse(val id: Int,
                            val name: String,
                            val duration: Time,
                            val numberMax: Int,
                            val price: Double,
                            val cid: Int,
                            val appointments: List<Int>,
                            val days: List<Int>,
                            val users: List<Int>
)