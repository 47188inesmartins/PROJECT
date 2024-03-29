package backend.jvm.services

import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.unavailability.UnavailabilityEntity
import backend.jvm.dao.*
import backend.jvm.model.appointment.AppointmentInputDto
import backend.jvm.model.appointment.AppointmentOutputDto
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.service.ServiceInputDto
import backend.jvm.model.service.ServiceOutputDto
import backend.jvm.model.user.AppointmentManager
import backend.jvm.model.user.UserEntity
import backend.jvm.model.user.UserOutputDto
import backend.jvm.services.interfaces.IAppointmentServices
import backend.jvm.utils.UserRoles
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
    lateinit var vacationDao: VacationDao

    @Autowired
    lateinit var companyDao: CompanyDao

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
        val appointmentDate = Date.valueOf(appointment.appDate)?: throw Exception("invalid date")

        if(!isDateAfterCurrent(appointmentDate)) throw InvalidDate()
        if(!verifyVacations(schedule.id,appointmentDate)) throw InvalidDate()

        val appointmentDb = appointment.mapToAppointmentDb(appointmentDate,appointment, schedule, listOf(userClient, employee), service)
        val savedAppointment = appointmentDao.save(appointmentDb)

        val endTime = addTimes(savedAppointment.appHour,service.duration)

        addUnavailabilityToEmployee(appointment.appDate,savedAppointment.appHour,endTime,employee)

        return AppointmentOutputDto(savedAppointment)
    }

    /**
     * Add an appointment as an employee or manager
     * @param appointmentEmployee The appointment details.
     * @param companyId The company ID.
     * @return The id of the created appointment.
     */
    @Transactional
    fun addAppointmentByEmployee(appointmentEmployee: AppointmentManager, companyId: Int):Int{
        val user = userDao.getUserById(appointmentEmployee.userId)
            ?: throw UserNotFound()
        val service = servicesRepository.getServiceDBById(appointmentEmployee.service)
            ?: throw ServiceNotFound()
        val schedule = scheduleDao.getScheduleByCompany_Id(companyId)
            ?: throw ScheduleNotFound()

        val appointmentDate = Date.valueOf(appointmentEmployee.appDate)?: throw Exception("invalid date")
        if(!isDateAfterCurrent(appointmentDate)) throw InvalidDate()
        if(!verifyVacations(schedule.id,appointmentDate)) throw InvalidDate()
        val appointmentHour = Time.valueOf(appointmentEmployee.appHour.plus(":00"))?: throw Exception("invalid hour")

        val appointmentDb = AppointmentEntity(appointmentHour, appointmentDate, schedule, listOf(user,null), service)
        val savedAppointment = appointmentDao.save(appointmentDb)
        return savedAppointment.id
    }

    /**
     * Deletes an appointment by ID.
     * @param id The appointment ID.
     * @throws AppointmentNotFound if the appointment is not found.
     */
    override fun deleteAppointment(id: Int) {
        val getAppointment = appointmentDao.findById(id).get()
        val company = companyDao.getCompanyBySchedule(getAppointment.schedule.id) ?: throw ScheduleNotFound()
        if (getAppointment.equals(Optional.empty<AppointmentEntity>())) throw AppointmentNotFound()
        if (getCurrentDate().after(getAppointment.appDate) && getCurrentHour() > getAppointment.appHour)
            return appointmentDao.deleteById(id)
        val employee = getAppointment.user!!.firstOrNull { user ->
            user?.let { it1 -> userDao.getUserDBByIdAndRole(UserRoles.EMPLOYEE.name, company, user.id) } != null ||
                    user?.let { it1 -> userDao.getUserDBByIdAndRole(UserRoles.MANAGER.name, it1.id, user.id) } != null
        }?: throw UserNotFound()
        appointmentDao.deleteById(id)
        unavailabilityDao.deleteByDateBeginAndHourBeginAndUser(getAppointment.appDate,getAppointment.appHour,employee)
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
    fun getAvailableServicesByAppointment(beginHour: String, date: String, companyId: Int): List<Pair<ServiceOutputDto, List<UserOutputDto>>> {
        val bh = Time.valueOf(beginHour.plus(":00"))
        val d = Date.valueOf(date)
        val weekDay = getDayOfWeek(d)
        val schedule = scheduleDao.getScheduleByCompany_Id(companyId) ?: throw ScheduleNotFound()
        val services = servicesRepository.getAllServicesFromACompany(companyId)
        //val day = dayDao.getDayByWeekDaysAndSchedule(weekDay, schedule)

        return services.map {
            val employees = userDao.getAvailableEmployeesByService(it.id, d, bh, addTimes(bh, it.duration))
            Pair(it, employees.map { user -> UserOutputDto(user) })
        }.filter {
            val day = it.first.day?.firstOrNull { day -> day.weekDays.trim() == weekDay.trim() }

            day != null && ((
                    it.second.isNotEmpty() &&
                (((day.beginHour) < addTimes(bh, it.first.duration) && addTimes(
                    bh,
                    it.first.duration
                ) < (day.intervalBegin ?: Time.valueOf("23:59:59"))) ||
                        ((day.intervalEnd ?: Time.valueOf("23:59:59")) <= addTimes(
                            bh,
                            it.first.duration
                        ) && addTimes(bh, it.first.duration) <= (day.endHour)))
            ))
        }.map { Pair(ServiceOutputDto(it.first), it.second) }
    }


    /**
     * Add an unvailability to the employee for the appointment added
     * @param date is the appointment date
     * @param hour is the appointment hour begin
     * @param endHour is the appointment hour end
     * @param employee designated for the appointment
     */
    private fun addUnavailabilityToEmployee(date:String,hour: Time,endHour: Time,employee: UserEntity){
        val unavailabilityEntity = UnavailabilityEntity(
            Date.valueOf(date),
            null,
            hour,
            endHour,
            employee
        )
        unavailabilityDao.save(unavailabilityEntity)
    }

    /**
     * Verifies if the appointment date is between vacations dates
     * @param scId Schedule associated with the company
     * @param appDate Date to verify
     * @return true if the date is not between vacations or false otherwise
     */
    private fun verifyVacations(scId: Int, appDate: Date):Boolean{
        val vacations = vacationDao.getVacationsByScheduleId(scId)
        if(vacations.isNotEmpty()){
            vacations.forEach {
                val dateB = it.dateBegin
                val dateE = it.dateEnd
                if(appDate.after(dateB)&&appDate.before(dateE)) return false
            }
        }
        return true
    }

    /**
    * Verifies if the date is after the current date
     * @param date to check if is after the current date
     * @return true if the date is after or false otherwise
     */
    private fun isDateAfterCurrent(date: Date): Boolean {
        val currentDate = Date(System.currentTimeMillis())
        return date.after(currentDate)
    }
}
