package backend.jvm.services

import backend.jvm.model.*
//import backend.jvm.repository.AppointmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
/*
@Service
class AppointmentServices {

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    fun addAppointment(appointment: Appointment): Appointment {
        return appointmentRepository.save(appointment)
    }

    fun deleteAppointment(id: Int): Boolean{
        appointmentRepository.deleteById(id)
        return true
    }

    fun findAllByClient(cid: Client) = appointmentRepository.findAllByCid(cid)

    fun findAllBySchedule(sid: Schedule) = appointmentRepository.findAllBySid(sid)

    fun findAllByEmployee(eid: Employee) = appointmentRepository.findAllByEid(eid)

    fun getCompany(id: Int): Company = appointmentRepository.getCompany(id)

}*/