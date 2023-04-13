package backend.jvm.controllers.json

import kotlinx.serialization.Serializable
import java.sql.Time


@Serializable
data class ScheduleResponse(val id: Int,
                            val compId: Int
)