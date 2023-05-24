package backend.jvm.services

import backend.jvm.model.ServiceDB
import backend.jvm.repository.AppointmentRepository
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.interfaces.IAppointmentServices
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Time
import java.util.*
import java.util.Calendar.*


@Service
class AppointmentServices : IAppointmentServices {

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var servicesRepository: ServiceRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    override fun addAppointment(appointment: AppointmentInputDto): AppointmentOutputDto {
        val service = servicesRepository.getServiceDBById(appointment.service)?: throw ServiceNotFound()
        val user = if (appointment.user != null) appointment.user.map {
            userRepository.getUserById(it)?: throw UserNotFound()
        } else null
        val schedule = scheduleRepository.getScheduleById(appointment.schedule)?: throw ScheduleNotFound()
        val app = appointment.mapToAppointmentDb(appointment, schedule, user, service)
        val savedAppointment = appointmentRepository.save(app)
        return AppointmentOutputDto(savedAppointment)
    }

    override fun deleteAppointment(id: Int){
        appointmentRepository.deleteById(id)
    }

    override fun getAppointment(id: Int): AppointmentOutputDto? {
        val isAppointment = appointmentRepository.findById(id)
        if(!isAppointment.isPresent) throw InvalidAppointment()
        val appointment = isAppointment.get()
        return AppointmentOutputDto(appointment)
    }

    override fun getAppointmentByDateAndHour (sid: Int, appHour: String, appDate: String) : List<AppointmentOutputDto> {
        val date = Date.valueOf(appDate)
        val hour = Time.valueOf(appHour)
        val appointments = appointmentRepository.getAppointmentByDateAndHour(sid, date, hour)
        if(appointments.isEmpty()) throw EmptyAppointments()
        return appointments.map{ AppointmentOutputDto(it) }
    }

    override fun getAvailableServicesByEmployees(beginHour:String, date:String, companyId: Int):List<ServiceOutputDto>{
        val bh = Time.valueOf(beginHour)
        val d = Date.valueOf(date)
        val dur = Time.valueOf("00:30:00")
        val weekDay = getDayOfWeek(d)
        val services = servicesRepository.getAllServicesFromACompany(companyId)

        //val s = servicesRepository.getServicesWithNonEmptyUserList(d, bh,  Time(bh.time+dur.time))


        // weekDay = :weekDay and


       // services.forEach { println(  userRepository.getAvailableEmployeesByService(it.id, d, bh, Time(bh.time+it.duration.time)).isNotEmpty()) }



        val serv = services.filter {
             userRepository.getAvailableEmployeesByService(it.id, d, bh, getEndHour(bh, dur)).isNotEmpty()
        }


      // serv.forEach { println(it.id) }

        return serv.map { ServiceOutputDto(it) }
    }


    override fun availableServicesByDay(companyId: Int, day: String,  beginHour: Time): List<ServiceDB>{
        val services = servicesRepository.getAvailableServicesByDay(companyId, day)
        TODO()
    }


    fun getEndHour(tempo1: Time, tempo2: Time): Time {
        val additionalTime = tempo2.time - tempo2.timezoneOffset * 60 * 1000
        println(Time(tempo1.time + additionalTime))
        return Time(tempo1.time + additionalTime)
    }

    fun getDayOfWeek(date : Date): String{
        val utilDate = java.util.Date(date.time)
        val calendar = Calendar.getInstance()
        calendar.time = utilDate
        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        println("Dia da semana: $dayOfWeek")

        return when(dayOfWeek) {
            SUNDAY -> "SUN"
            MONDAY -> "MON"
            TUESDAY -> "TUE"
            WEDNESDAY -> "WED"
            THURSDAY -> "THU"
            FRIDAY -> "FRI"
            SATURDAY -> "SAT"
            else -> "invalid data"
        }
    }
}