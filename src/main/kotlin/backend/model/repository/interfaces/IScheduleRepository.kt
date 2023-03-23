package backend.model.repository.interfaces

import backend.domain.DB.ScheduleDB

interface IScheduleRepository {
    fun get(id: Int): ScheduleDB?
    fun remove(id: Int) : Boolean
    fun add(schedule: ScheduleDB) : Int?
}