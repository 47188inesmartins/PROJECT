package backend.jvm.services

import backend.jvm.model.Appointment
import backend.jvm.repository.AppointmentRepository
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Time
import java.util.*
import java.util.Calendar.*


@Service
class AppointmentServices{

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var servicesRepository: ServiceRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

     fun addAppointment(appointment: AppointmentInputDto): AppointmentOutputDto {
        println("appointmentinputdto "+ appointment.service + appointment.schedule)
        val service = servicesRepository.getServiceDBById(appointment.service)
        val user = if (appointment.user != null) userRepository.getUserById(appointment.user) else null
        val schedule = scheduleRepository.getScheduleById(appointment.schedule)//scheduleService.getSchedule(appointment.schedule)!!
        val app = Appointment(
            appHour = Time.valueOf(appointment.appHour),
            appDate = Date.valueOf(appointment.appDate),
            scheduleId = schedule,
            userDBId = user,
            serviceDB = service
        )//service.duration
        val savedAppointment = appointmentRepository.save(app)
        return AppointmentOutputDto(savedAppointment)
    }

   /* fun getServicesByDateAndHour(appDate: String, appHour: String): Int {
        val date = Date.valueOf(appDate)
        val hour = Time.valueOf(appHour)
        return appointmentRepository.getNumberOfServicesByDateAndHour(serviceId, date, hour)
    }*/

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

    fun getAvailableServicesByEmployees(beginHour:String, date:String, companyId: Int):List<ServiceOutputDto>{
        val bh = Time.valueOf(beginHour)
        val d = Date.valueOf(date)
        val dur = Time.valueOf("00:30:00")

        val services = servicesRepository.getAllServicesFromACompany(companyId)

        services.forEach { println(  userRepository.getAvailableEmployeesByService(it.id, d, bh, Time(bh.time+it.duration.time)).isNotEmpty()) }
        println("time " + Time(bh.time - dur.time))
        println("bh " + Time(bh.time))
        val serv = services.filter {
             userRepository.getAvailableEmployeesByService(it.id, d, bh, getEndHour(bh, dur)).isNotEmpty()
        }
        println("hello")

        serv.forEach { println(it.id) }

        return serv.map { ServiceOutputDto(it) }
    }

 /*   fun getAvailableServicesByDay(beginHour:String, date:String, companyId: Int){
        val bh = Time.valueOf(beginHour)
        val d = Date.valueOf(date)
        val dur = Time.valueOf("00:30:00")
        val response = servicesRepository.getAvailableServicesByDay(companyId)

        val serv = services.filter {
            servicesRepository.getAvailableServicesByDay(com, d, bh, getEndHour(bh, dur)).isNotEmpty()
        }
        val weekDay = getDayOfWeek(d)

    }
*/


    fun getEndHour(tempo1: Time, tempo2: Time): Time {
        val additionalTime = tempo2.time - tempo2.timezoneOffset * 60 * 1000
        println(Time(tempo1.time + additionalTime))
      /*  val totalMillis = tempo1.time - tempo2.time
        val t = Time(totalMillis)
        println(t)
        return t*/
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