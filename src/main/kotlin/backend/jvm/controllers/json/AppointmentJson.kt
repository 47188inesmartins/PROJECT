package backend.jvm.controllers.json

import backend.jvm.model.Services
import kotlinx.serialization.Serializable
import java.sql.Date
import java.sql.Time



@Serializable
data class AppointmentResponse(val id: Int, val appHour: Time, val appDate: Date, val sid: Int, val uid: Int?)
@Serializable
data class AppointmentRequest(val appHour: Time, val appDate: Date, val sid: Int, val uid: Int, val services: List<Services>)