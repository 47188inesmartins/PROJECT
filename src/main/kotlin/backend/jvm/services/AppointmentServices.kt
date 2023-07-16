package backend.jvm.services

import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.unavailability.UnavailabilityEntity
import backend.jvm.dao.*
import backend.jvm.model.appointment.AppointmentInputDto
import backend.jvm.model.appointment.AppointmentOutputDto
import backend.jvm.model.service.ServiceOutputDto
import backend.jvm.model.user.AppointmentManager
import backend.jvm.model.user.UserOutputDto
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
    lateinit var appointmentDao: AppointmentDao

    @Autowired
    lateinit var servicesRepository: ServiceDao

    @Autowired
    lateinit var userDao: UserDao

    @Autowired
    lateinit var scheduleDao: ScheduleDao

    @Autowired
    lateinit var unavailabilityDao: UnavailabilityDao

    @Autowired
    lateinit var dayDao: DayDao

    @Autowired
    lateinit var vacationDao: VacationDao

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
        val userClient = userDao.getUserByToken(UUID.fromString(token))
        val service = servicesRepository.getServiceDBById(appointment.service)
            ?: throw ServiceNotFound()
        if (appointment.user == null) throw InvalidAppointment()
        val employee = userDao.getUserById(appointment.user) ?: throw UserNotFound()

        val schedule = scheduleDao.getScheduleById(cid)
            ?: throw ScheduleNotFound()

        val appointmentDb = appointment.mapToAppointmentDb(appointment, schedule, listOf(userClient, employee), service)
        val savedAppointment = appointmentDao.save(appointmentDb)

        val endTime = savedAppointment.appHour.time + service.duration.time
        val time = Time(endTime)

        val unavailabilityEntity = UnavailabilityEntity(
            Date.valueOf(appointment.appDate),
            null,
            savedAppointment.appHour,
            time,
            employee
        )
        unavailabilityDao.save(unavailabilityEntity)
        return AppointmentOutputDto(savedAppointment)
    }

    @Transactional
    fun addAppointmentByEmployee(appointmentEmployee: AppointmentManager, companyId: Int):Int{
        //employee
        val user = userDao.getUserById(appointmentEmployee.userId)
            ?: throw UserNotFound()
        val service = servicesRepository.getServiceDBById(appointmentEmployee.service)
            ?: throw ServiceNotFound()
        val schedule = scheduleDao.getScheduleById(companyId)
            ?: throw ScheduleNotFound()

        val appointmentDate = Date.valueOf(appointmentEmployee.appDate)?: throw Exception("invalid date")
        if(!isDateAfterCurrent(appointmentDate)) throw InvalidDate()
        val appointmentHour = Time.valueOf(appointmentEmployee.appHour.plus(":00"))?: throw Exception("invalid hour")

        val vacations = vacationDao.getVacationsByScheduleId(schedule.id)
        if(vacations.isNotEmpty()){
            vacations.forEach {
                val dateB = it.dateBegin
                val dateE = it.dateEnd
                if(appointmentDate.after(dateB)&&appointmentDate.before(dateE)) throw InvalidDate()
            }
        }
        val appointmentDb = AppointmentEntity(appointmentHour,appointmentDate,schedule, listOf(user,null),service)
        val savedAppointment = appointmentDao.save(appointmentDb)
        return savedAppointment.id
    }

   /* @Transactional
    fun addOwnAppointment(token:String?, appointmentEmployee: AppointmentEmployee, companyId: Int):Int{
        if(token == null) throw UserNotFound()
        val userId = userDao.getUserByToken(UUID.fromString(token))!!
        //employee
        val service = servicesRepository.getServiceDBById(appointmentEmployee.service)
            ?: throw ServiceNotFound()
        val schedule = scheduleDao.getScheduleById(companyId)
            ?: throw ScheduleNotFound()

        val appointmentDate = Date.valueOf(appointmentEmployee.appDate)?: throw Exception("invalid date")
        if(!isDateAfterCurrent(appointmentDate)) throw InvalidDate()
        val appointmentHour = Time.valueOf(appointmentEmployee.appHour.plus(":00"))?: throw Exception("invalid hour")

        val vacations = vacationDao.getVacationsByScheduleId(schedule.id)
        if(vacations.isNotEmpty()){
            vacations.forEach {
                val dateB = it.dateBegin
                val dateE = it.dateEnd
                if(appointmentDate.after(dateB)&&appointmentDate.before(dateE)) throw InvalidDate()
            }
        }
        val appointmentDb = AppointmentEntity(appointmentHour,appointmentDate,schedule, listOf(user,null),service)
        val savedAppointment = appointmentDao.save(appointmentDb)
        return savedAppointment.id
    }*/

    private fun isDateAfterCurrent(date: Date): Boolean {
        val currentDate = Date(System.currentTimeMillis())
        return date.after(currentDate)
    }

    /**
     * Deletes an appointment by ID.
     * @param id The appointment ID.
     * @throws AppointmentNotFound if the appointment is not found.
     */
    override fun deleteAppointment(id: Int) {
        val getAppointment = appointmentDao.findById(id).get()
        if (getAppointment.equals(Optional.empty<AppointmentEntity>())) throw AppointmentNotFound()
        if (getCurrentDate().after(getAppointment.appDate) && getCurrentHour() > getAppointment.appHour)
            return appointmentDao.deleteById(id)
        appointmentDao.deleteById(id)
    }

    /**
     * Get an appointment by ID.
     * @param id The appointment ID.
     * @return The appointment with the specified ID.
     * @throws AppointmentNotFound if the appointment is not found.
     */
    override fun getAppointmentById(id: Int): AppointmentOutputDto? {
        val isAppointment = appointmentDao.findById(id)
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
        val appointments = appointmentDao.getAppointmentByDateAndHour(sid, date, hour)
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
        val schedule = scheduleDao.getScheduleByCompany_Id(companyId) ?: throw ScheduleNotFound()
        val services = servicesRepository.getAllServicesFromACompany(companyId)
        val day = dayDao.getDayByWeekDaysAndSchedule(weekDay, schedule)

        return services.map {
            val employees = userDao.getAvailableEmployeesByService(it.id, d, bh, addTimes(bh, it.duration))
            Pair(ServiceOutputDto(it), employees.map { user -> UserOutputDto(user) })
        }.filter{ (it.second.isNotEmpty() &&
                ((day.beginHour < addTimes(bh, it.first.duration) && addTimes(bh, it.first.duration) < day.intervalBegin) ||
                        (day.intervalEnd!! <= addTimes(bh, it.first.duration) && addTimes(bh, it.first.duration) <= day.endHour)))
        }
    }
}
