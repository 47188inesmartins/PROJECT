package backend.repository

import backend.domain.Company
import backend.domain.DB.CompanyDB
import backend.domain.DB.ScheduleDB

interface ScheduleRepository {
    fun get(id: Int): ScheduleDB?
    fun remove(id: Int) : Boolean
    fun add(schedule: ScheduleDB) : Int?
}