package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.AppointmentRepository
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.sql.Date

@Service
class AppointmentServices{

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var servicesRepository: ServiceRepository

    @Autowired
    lateinit var userService: UserServices

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    fun addAppointment(appointment: AppointmentInputDto): AppointmentOutputDto {
        val service = servicesRepository.getServiceDBById(appointment.service)
        val user = if (appointment.user != null) userService.findById(appointment.user) else null
        val schedule = scheduleRepository.getReferenceById(appointment.schedule)//scheduleService.getSchedule(appointment.schedule)!!
        val app = Appointment(
            appHour = Time.valueOf(appointment.appHour),
            appDate = Date.valueOf(appointment.appDate),
            scheduleId = schedule,
            userId = user,
            serviceDB = service
        )
        val savedAppointment = appointmentRepository.save(app)
        return AppointmentOutputDto(savedAppointment)
    }

    fun getNumberOfServicesByDateAndHour(serviceId: Int, appDate: String, appHour: String): Int {
        val date = Date.valueOf(appDate)
        val hour = Time.valueOf(appHour)
        return appointmentRepository.getNumberOfServicesByDateAndHour(serviceId, date, hour)
    }

    fun deleteAppointment(id: Int){
        appointmentRepository.deleteById(id)
    }

    fun getAppointment(id: Int): AppointmentOutputDto? {
        val isAppointment = appointmentRepository.findById(id)
        if(!isAppointment.isPresent) return null
        val appointment = isAppointment.get()
        return AppointmentOutputDto(appointment)
    }

    fun getAppointmentByDateAndHour (sid: Int, appHour: String, appDate: String) : List<AppointmentOutputDto> {
        val date = Date.valueOf(appDate)
        val hour = Time.valueOf(appHour)
        val appointments = appointmentRepository.getAppointmentByDateAndHour(sid, date, hour)
        return appointments.map{ AppointmentOutputDto(it) }
    }
}