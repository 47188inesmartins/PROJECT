package backend.jvm.controllers.json

import kotlinx.serialization.Serializable
import java.sql.Date
import java.sql.Time

@Serializable
data class VacationResponse(val id: Int,
                            val dateBegin: Date,
                            val endHour: Date,
                            val sid: Int
)