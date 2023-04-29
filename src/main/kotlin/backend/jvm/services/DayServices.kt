package backend.jvm.services

import backend.jvm.model.Day
import backend.jvm.repository.DayRepository
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.DayOutputDto
import backend.jvm.utils.errorHandling.InvalidDay
import backend.jvm.utils.errorHandling.InvalidOpenDay
import backend.jvm.utils.errorHandling.ScheduleInvalid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time

@Service
class DayServices {
    companion object{
        val listDayOfWeek = listOf("MON","TUE","WED","THU","FRI","SAT","SUN")
    }

    @Autowired
    lateinit var dayRepository:DayRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var serviceRepository: ServiceRepository

    fun addOpenDay(day: DayInputDto):DayOutputDto{
        if(!listDayOfWeek.contains(day.weekDays.uppercase())) throw InvalidOpenDay()
        val schedule = day.schedule?.let { scheduleRepository.findById(it).get() }
        val service = day.service?.let { serviceRepository.findById(it).get() }
        if(schedule == null) throw ScheduleInvalid()
        val db = dayRepository.save(day.mapToDayDb(day,schedule,service))
        return  DayOutputDto(db)
    }

    fun updateBeginHour(id:Int,hour: String): Time {
        if(dayRepository.getDayById(id) == null ) throw InvalidDay()
        return dayRepository.updateBeginHour(id, Time.valueOf(hour))
    }

    fun updateEndHour(id:Int,hour: String): Time {
        if(dayRepository.getDayById(id) == null ) throw InvalidDay()
        return dayRepository.updateEndHour(id,Time.valueOf(hour))
    }

    fun deleteDay(day: Int){
        dayRepository.deleteById(day)
    }

}