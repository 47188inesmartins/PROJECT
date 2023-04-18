package backend.jvm.services

import backend.jvm.model.Schedule
import backend.jvm.repository.*
import backend.jvm.services.dto.ScheduleInputDto
import backend.jvm.services.dto.ScheduleOutputDto
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import org.springframework.stereotype.Service

@Service
class ScheduleServices {

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository
    @Autowired
    lateinit var companyRepository: CompanyRepository
    @Autowired
    lateinit var dayRepository: DayRepository
    @Autowired
    lateinit var appointmentRepository: AppointmentRepository
    @Autowired
    lateinit var vacationRepository: VacationRepository

    /**
     * add a schedule
     * @param schedule object to save
     * @return Schedule if the save occurs with success
     * @exception IllegalArgumentException – in case the given entity is null
     */

    fun addSchedule(schedule: ScheduleInputDto): ScheduleOutputDto {
        val company = companyRepository.getReferenceById(schedule.companyId)
        val day = schedule.day.map { dayRepository.getReferenceById(it) }
        val app = schedule.appointment.map { appointmentRepository.getReferenceById(it) }
        val vacation = schedule.vacation.map { vacationRepository.getReferenceById(it) }

        val scheduleDb = schedule.mapToSchedule(company ,app ,day ,vacation)
        return ScheduleOutputDto(scheduleRepository.save(scheduleDb))

    }

    /**
     * deletes a schedule
     * @param id that represents the id of a schedule
     * @exception IllegalArgumentException – in case the given id is null
     */
    fun deleteSchedule(id: Int){
        scheduleRepository.deleteById(id)
    }

    /**
     * fins the schedule by the id
     * @param id that represents the id of a schedule
     * @return Schedule which is the correspondent schedule for that id
     * @exception NoSuchElementException – if no value is present
     */
    fun getSchedule(id:Int): ScheduleOutputDto?{
        return ScheduleOutputDto(scheduleRepository.findById(id).get())
    }

    /*fun getServices(idSchedule: Int,idService: Int): List<backend.jvm.model.ServiceDB>{
       TODO()// return scheduleRepository.getAllServices(idSchedule,idService)
    }*/

    /*  fun getAllClientSchedule(id:Int,date: String,hour: String): List<User>{
      TODO()
      //return scheduleRepository.getAllClientSchedule(id,date,hour)
    }*/

    /*fun findScheduleByAppointment(appointment: MutableList<Appointment>): Schedule{
        return scheduleRepository.findScheduleByAppointment(appointment)
    }*/
}