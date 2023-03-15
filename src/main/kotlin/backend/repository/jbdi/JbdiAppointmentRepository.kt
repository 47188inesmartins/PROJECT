package backend.repository.jbdi

import backend.domain.Appointment
import backend.domain.Client
import backend.domain.Schedule
import backend.repository.AppointmentRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.sql.Time
import java.util.*

class JbdiAppointmentRepository(private val handle: Handle): AppointmentRepository {

    override fun getByEmpresa(empresa: String): Int {
        TODO("Not yet implemented")
    }

    override fun getByCliente(cliente: String): Int {
        TODO("Not yet implemented")
    }

    override fun remove(empresa: String, cliente: String, date: Date, hour: Date): Int? {
        TODO("Not yet implemented")
    }

    override fun add(appointment: Appointment): Int? {
        return handle.createQuery("insert into dbo.appointment(app_number, app_hour, availability, sid, cid) values (:app_number, :app_hour, :availability, :sid,:cid) returning id"
            )
            .bind("app_number", appointment.appNumber)
            .bind("app_hour", appointment.appHour)
            .bind("availability", appointment.availability)
            .bind("sid", appointment.sid)
            .bind("cid", appointment.cid)
            .mapTo<Int>()
            .first()
    }
}