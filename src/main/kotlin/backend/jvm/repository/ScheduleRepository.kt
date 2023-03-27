package backend.jvm.repository

import backend.jvm.model.Company
import backend.jvm.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository: JpaRepository<Schedule, Int> {

    fun getCompany(compId: Int): Company

}