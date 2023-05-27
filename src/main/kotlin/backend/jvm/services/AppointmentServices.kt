package backend.jvm.services

import backend.jvm.model.ServiceDB
import backend.jvm.model.UnavailabilityDB
import backend.jvm.repository.*
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.interfaces.IAppointmentServices
import backend.jvm.utils.UserRoles
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Time
import java.time.LocalDate
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

    @Autowired
    lateinit var unavailabilityRepository: UnavailabilityRepository

    override fun addAppointment(appointment: AppointmentInputDto): AppointmentOutputDto {
        val service = servicesRepository.getServiceDBById(appointment.service)
            ?: throw ServiceNotFound()

        val user = appointment.user?.map { userId ->
            userRepository.getUserById(userId) ?: throw UserNotFound()
        }

        val employee = user?.firstOrNull { user ->
            val role = userRepository.getRole(user.id)
            role in listOf(UserRoles.EMPLOYEE.name, UserRoles.MANAGER.name)
        }

        val schedule = scheduleRepository.getScheduleById(appointment.schedule)
            ?: throw ScheduleNotFound()

        val appointmentDb = appointment.mapToAppointmentDb(appointment, schedule, user, service)
        val savedAppointment = appointmentRepository.save(appointmentDb)

        val endTime = savedAppointment.appHour.time + service.duration.time
        val time = Time(endTime)

        val unavailabilityDb = UnavailabilityDB(
            getCurrentDate(),
            null,
            savedAppointment.appHour,
            time,
            employee ?: throw UserNotFound()
        )
        unavailabilityRepository.save(unavailabilityDb)

        return AppointmentOutputDto(savedAppointment)
    }

    fun getCurrentDate(): Date = Date.valueOf(LocalDate.now())

    fun getCurrentHour(): Time{
        val currentTime = getInstance().time
        return Time(currentTime.time)
    }


    override fun deleteAppointment(id: Int){
        val getAppointment = appointmentRepository.getAppointmentById(id)
        if(getCurrentDate().after(getAppointment.appDate) && getCurrentHour() > getAppointment.appHour)
            return appointmentRepository.deleteById(id)

        val getEmployee = getAppointment.usersDB?.first {
            val role = userRepository.getRole(it.id)
            role == UserRoles.EMPLOYEE.name ||role == UserRoles.MANAGER.name
        }


        if(getEmployee == null) InvalidAppointment()
        val unavailabilityDB = unavailabilityRepository.getUnavailabilityDBByUserDBId(getEmployee!!)
        unavailabilityRepository.deleteById(unavailabilityDB.id)
        appointmentRepository.deleteById(id)
    }

    override fun getAppointment(id: Int): AppointmentOutputDto? {
        val isAppointment = appointmentRepository.findById(id)
        if(!isAppointment.isPresent) throw AppointmentNotFound()
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

    override fun getAvailableServicesByEmployees(
        beginHour: String,
        date: String,
        companyId: Int
    ): List<ServiceOutputDto> {
        TODO("Not yet implemented")
    }

    override fun getAvailableServices(beginHour:String, date:String, companyId: Int):List<ServiceOutputDto>{
        val bh = Time.valueOf(beginHour)
        val d = Date.valueOf(date)
        val dur = Time.valueOf("00:30:00")
        val weekDay = getDayOfWeek(d)
        val services = servicesRepository.getAvailableServicesByHour(weekDay, bh, companyId)
        val serv = services.filter {
             userRepository.getAvailableEmployeesByService(it.id, d, bh, getEndHour(bh, dur)).isNotEmpty()
        }
        return serv.map { ServiceOutputDto(it) }
    }


    override fun availableServicesByDay(companyId: Int, day: String,  beginHour: Time): List<ServiceDB>{
        val services = servicesRepository.getAvailableServicesByDay(companyId, day)
        TODO()
    }

    override fun getAllAppointmentsByUser(user: Int): List<AppointmentOutputDto> {
        TODO("Not yet implemented")
    }


    fun getEndHour(tempo1: Time, tempo2: Time): Time {
        val additionalTime = tempo2.time - tempo2.timezoneOffset * 60 * 1000
        println(Time(tempo1.time + additionalTime))
        return Time(tempo1.time + additionalTime)
    }

    fun getDayOfWeek(date : Date): String{
        val utilDate = java.util.Date(date.time)
        val calendar = getInstance()
        calendar.time = utilDate
        val dayOfWeek = calendar[DAY_OF_WEEK]
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