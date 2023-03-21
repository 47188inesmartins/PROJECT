package backend

import backend.repository.AppointmentRepository
import backend.repository.CompanyRepository
import backend.repository.ScheduleRepository

interface Transaction {

    val appointmentRepository: AppointmentRepository

    val companyRepository: CompanyRepository

    val scheduleRepository: ScheduleRepository

    fun rollback()
}