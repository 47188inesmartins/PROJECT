package backend

import backend.model.repository.interfaces.IAppointmentRepository
import backend.model.repository.CompanyRepository
import backend.model.repository.interfaces.IScheduleRepository

interface Transaction {

    val appointmentRepository: IAppointmentRepository

    val companyRepository: CompanyRepository

    val scheduleRepository: IScheduleRepository

    fun rollback()
}