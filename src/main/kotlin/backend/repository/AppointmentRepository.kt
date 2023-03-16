package backend.repository

import backend.domain.Appointment
import backend.domain.DB.AppointmentDB
import java.sql.Time
import java.util.*

interface AppointmentRepository{
    fun getAppByCompany(company: Int): List<AppointmentDB>
    fun getAppByClient(client: Int): List<AppointmentDB>
    fun remove(appointment: AppointmentDB): Int?
    fun add(appointment: Appointment): AppointmentDB?
}