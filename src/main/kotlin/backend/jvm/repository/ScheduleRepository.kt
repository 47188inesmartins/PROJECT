package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository: JpaRepository<Schedule, Int> {

        override fun getById(id: Int): Schedule

        fun findScheduleByAppointment(appointment: MutableList<Appointment>): Schedule
}