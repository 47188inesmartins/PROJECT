package backend.repository

import backend.domain.Appointment
import java.sql.Time
import java.util.*

interface AppointmentRepository{
    fun getByEmpresa(empresa: String): Int
    fun getByCliente(cliente: String): Int
    fun remove(empresa: String, cliente: String, date: Date, hour: Date) : Int?
    fun add(appointment: Appointment): Int?
}