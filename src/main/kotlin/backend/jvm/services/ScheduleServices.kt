package backend.jvm.services

import backend.jvm.dao.*
import backend.jvm.model.schedule.ScheduleInputDto
import backend.jvm.model.schedule.ScheduleOutputDto
import backend.jvm.services.interfaces.IScheduleServices
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import org.springframework.stereotype.Service

@Service
class ScheduleServices: IScheduleServices {

    @Autowired
    lateinit var scheduleDao: ScheduleDao
    @Autowired
    lateinit var companyDao: CompanyDao
    @Autowired
    lateinit var dayDao: DayDao
    @Autowired
    lateinit var appointmentDao: AppointmentDao
    @Autowired
    lateinit var vacationDao: VacationDao

    /**
     * add a schedule
     * @param schedule object to save
     * @return Schedule if the save occurs with success
     * @exception IllegalArgumentException – in case the given entity is null
     */

    override fun addSchedule(schedule: ScheduleInputDto): ScheduleOutputDto {
        val company = companyDao.getReferenceById(schedule.companyId)
        val day = schedule.day?.map { dayDao.getReferenceById(it) }
        val app = schedule.appointment?.map { appointmentDao.getReferenceById(it) }
        val vacation = schedule.vacation?.map { vacationDao.getReferenceById(it) }

        val scheduleDb = schedule.mapToSchedule(company ,app ,day ,vacation)
        return ScheduleOutputDto(scheduleDao.save(scheduleDb))

    }

    /**
     * deletes a schedule
     * @param id that represents the id of a schedule
     * @exception IllegalArgumentException – in case the given id is null
     */
    override fun deleteSchedule(id: Int){
        scheduleDao.deleteById(id)
    }

    /**
     * fins the schedule by the id
     * @param id that represents the id of a schedule
     * @return Schedule which is the correspondent schedule for that id
     * @exception NoSuchElementException – if no value is present
     */
    override fun getScheduleById(id:Int): ScheduleOutputDto?{
        return ScheduleOutputDto(scheduleDao.findById(id).get())
    }
}