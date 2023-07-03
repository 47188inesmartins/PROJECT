package backend.jvm.services

import backend.jvm.model.Appointment
import backend.jvm.model.UnavailabilityDB
import backend.jvm.repository.*
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.services.interfaces.IAppointmentServices
import backend.jvm.utils.errorHandling.*
import backend.jvm.utils.time.getDayOfWeek
import backend.jvm.utils.time.addTimes
import backend.jvm.utils.time.getCurrentDate
import backend.jvm.utils.time.getCurrentHour
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Time
import java.util.*

/**
 * Service class for managing appointments.
 */
@Service
@Transactional
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

    @Autowired
    lateinit var dayRepository: DayRepository

    /**
     * Add an appointment.
     * @param appointment The appointment details.
     * @param cid The company ID.
     * @param token The authentication token.
     * @return The created appointment.
     * @throws ServiceNotFound if the service is not found.
     * @throws InvalidAppointment if the appointment is invalid.
     * @throws UserNotFound if the user is not found.
     * @throws ScheduleNotFound if the schedule is not found.
     */
    @Transactional
    override fun addAppointment(appointment: AppointmentInputDto, cid: Int, token: String): AppointmentOutputDto {
        val userClient = userRepository.getUserByToken(UUID.fromString(token))
        val service = servicesRepository.getServiceDBById(appointment.service)
            ?: throw ServiceNotFound()
        if (appointment.user == null) throw InvalidAppointment()
        val employee = userRepository.getUserById(appointment.user) ?: throw UserNotFound()

        val schedule = scheduleRepository.getScheduleById(cid)
            ?: throw ScheduleNotFound()

        val appointmentDb = appointment.mapToAppointmentDb(appointment, schedule, listOf(userClient, employee), service)
        val savedAppointment = appointmentRepository.save(appointmentDb)

        val endTime = savedAppointment.appHour.time + service.duration.time
        val time = Time(endTime)

        val unavailabilityDb = UnavailabilityDB(
            Date.valueOf(appointment.appDate),
            null,
            savedAppointment.appHour,
            time,
            employee
        )
        unavailabilityRepository.save(unavailabilityDb)

        return AppointmentOutputDto(savedAppointment)
    }

    /**
     * Deletes an appointment by ID.
     * @param id The appointment ID.
     * @throws AppointmentNotFound if the appointment is not found.
     */
    override fun deleteAppointment(id: Int) {
        val getAppointment = appointmentRepository.findById(id).get()
        if (getAppointment.equals(Optional.empty<Appointment>())) throw AppointmentNotFound()
        if (getCurrentDate().after(getAppointment.appDate) && getCurrentHour() > getAppointment.appHour)
            return appointmentRepository.deleteById(id)
        appointmentRepository.deleteById(id)
    }

    /**
     * Get an appointment by ID.
     * @param id The appointment ID.
     * @return The appointment with the specified ID.
     * @throws AppointmentNotFound if the appointment is not found.
     */
    override fun getAppointmentById(id: Int): AppointmentOutputDto? {
        val isAppointment = appointmentRepository.findById(id)
        if (!isAppointment.isPresent) throw AppointmentNotFound()
        val appointment = isAppointment.get()
        return AppointmentOutputDto(appointment)
    }

    /**
     * Get appointments by date and hour.
     * @param sid The schedule ID.
     * @param appHour The appointment hour.
     * @param appDate The appointment date.
     * @return The list of appointments.
     * @throws EmptyAppointments if no appointments are found.
     */
    override fun getAppointmentByDateAndHour(sid: Int, appHour: String, appDate: String): List<AppointmentOutputDto> {
        val date = Date.valueOf(appDate)
        val hour = Time.valueOf(appHour)
        val appointments = appointmentRepository.getAppointmentByDateAndHour(sid, date, hour)
        if (appointments.isEmpty()) throw EmptyAppointments()
        return appointments.map { AppointmentOutputDto(it) }
    }

    /**
     * Gets an available services by appointment details.
     * @param beginHour The beginning hour.
     * @param date The date.
     * @param companyId The company ID.
     * @return The list of available services and their corresponding employees.
     * @throws ScheduleNotFound if the schedule is not found.
     */

    override fun getAvailableServicesByAppointment(beginHour: String, date: String, companyId: Int): List<Pair<ServiceOutputDto, List<UserOutputDto>>> {
        val bh = Time.valueOf(beginHour.plus(":00"))
        val d = Date.valueOf(date)
        val weekDay = getDayOfWeek(d)
        val schedule = scheduleRepository.getScheduleByCompany_Id(companyId) ?: throw ScheduleNotFound()
        val services = servicesRepository.getNormalScheduleServices(companyId)
        val day = dayRepository.getDayByWeekDaysAndAndSchedule(weekDay, schedule)

        val s = services.map {
            val employees = userRepository.getAvailableEmployeesByService(it.id, d, bh, addTimes(bh, it.duration))
            Pair(ServiceOutputDto(it), employees.map { user -> UserOutputDto(user) })
        }.filter { (it.second.isNotEmpty() &&
                ((day.beginHour < addTimes(bh, it.first.duration) && addTimes(bh, it.first.duration) < day.intervalBegin) ||
                        (day.intervalEnd!! < addTimes(bh, it.first.duration) && addTimes(bh, it.first.duration) < day.endHour))) }

        return emptyList()
    }
}
