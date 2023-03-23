package backend.model.repository.interfaces

import backend.domain.Appointment
import backend.domain.DB.AppointmentDB

interface IAppointmentRepository{
    fun getAppByCompany(company: Int): List<AppointmentDB>
    fun getAppByClient(client: Int): List<AppointmentDB>
    fun remove(appointment: AppointmentDB): Int?
    fun add(appointment: Appointment): AppointmentDB?
    fun get(id: Int): Appointment
}