package backend.jvm.services

import backend.jvm.model.Schedule
import backend.jvm.model.Services
import backend.jvm.model.User
import backend.jvm.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import org.springframework.stereotype.Service

@Service
class ScheduleServices {

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository


    /**
     * add a schedule
     * @param schedule object to save
     * @return Schedule if the save occurs with success
     * @exception IllegalArgumentException – in case the given entity is null
     */

    fun addSchedule(schedule: Schedule):Schedule{
        return scheduleRepository.save(schedule)
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
    fun getSchedule(id:Int): Schedule?{
        return scheduleRepository.findById(id).get()
    }

    fun getServices(idSchedule: Int,idService: Int): List<Services>{
        return scheduleRepository.getAllServices(idSchedule,idService)
    }

    /*  fun getAllClientSchedule(id:Int,date: String,hour: String): List<User>{
      TODO()
      //return scheduleRepository.getAllClientSchedule(id,date,hour)
    }*/

    /*fun findScheduleByAppointment(appointment: MutableList<Appointment>): Schedule{
        return scheduleRepository.findScheduleByAppointment(appointment)
    }*/
}