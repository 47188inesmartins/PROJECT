package backend.model.repository.jbdi

import backend.domain.Appointment
import backend.domain.DB.AppointmentDB
import backend.model.repository.interfaces.IAppointmentRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo


class JbdiAppointmentRepository(private val handle: Handle): IAppointmentRepository {

    override fun getAppByCompany(company: Int): List<AppointmentDB> {
        return handle.createQuery("select * from dbo.appointment where cid=:comp ")
            .bind("comp", company)
            .mapTo<AppointmentDB>()
            .list()
    }

    override fun getAppByClient(client: Int): List<AppointmentDB> {
        return handle.createQuery("select * from dbo.appointment where cid=:client ")
            .bind("client", client)
            .mapTo<AppointmentDB>()
            .list()

    }

    override fun remove(appointment: AppointmentDB): Int? {
        return handle.createQuery("delete from dbo.appointment where id=:app_id ")
            .bind("app_id", appointment.id)
            .mapTo<Int>()
            .first()
    }

    override fun add(appointment: Appointment): AppointmentDB? {
        return handle.createQuery("insert into dbo.appointment(number_app_persons, app_hour, availability, sid, cid) values (:app_number, :app_hour, :availability, :sid,:cid) returning id"
            )
            .bind("number_app_persons", appointment.numberAppPeople)
            .bind("app_hour", appointment.appHour)
            .bind("availability", appointment.availability)
            .bind("sid", appointment.sid)
            .bind("cid", appointment.cid)
            .mapTo<AppointmentDB>()
            .first()
    }

    override fun get(id: Int): Appointment {
        TODO("Not yet implemented")
    }
}