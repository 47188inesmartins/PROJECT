package backend.repository.jbdi

import backend.domain.DB.ScheduleDB
import backend.repository.ScheduleRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo

class JbdiScheduleRepository(private val handle: Handle): ScheduleRepository {
    override fun get(id: Int): ScheduleDB? {
        return handle.createQuery("select * from dbo.schedule where comp_id = :id")
            .bind("id", id)
            .mapTo<ScheduleDB>()
            .singleOrNull()
    }

    override fun remove(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(schedule: ScheduleDB): Int? {
        return handle.createQuery("insert into dbo.schedule ( begin_hour, end_hour, shc_interval, duration, comp_id) " +
                "values (:begin_hour, :end_hour, :shc_interval, :duration, :comp_id) returning id"
        )
            .bind("begin_hour", schedule.beginHour)
            .bind("end_hour", schedule.endHour)
            .bind("shc_interval", schedule.shcInterval)
            .bind("duration", schedule.duration)
            .bind("comp_id", schedule.compId)
            .mapTo<Int>()
            .first()
    }

}