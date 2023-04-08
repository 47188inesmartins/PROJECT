package backend.jvm.services

import backend.jvm.model.Day
import backend.jvm.repository.DayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time

@Service
class DayServices {
    companion object{
        val listDayOfWeek = listOf<String>("MON","TUE","WED","THU","FRI","SAT","SUN")
    }


    @Autowired
    lateinit var dayRepository:DayRepository

    fun addOpenDay(day: Day):Day{
        if(!listDayOfWeek.contains(day.weekDays.uppercase())) throw Exception("Invalid day of week")
        return dayRepository.save(day)
    }

    fun updateBeginHour(id:Int,hour: String):Day{
        return dayRepository.updateBeginHour(id, Time.valueOf(hour))
    }

    fun updateEndHour(id:Int,hour: String):Day{
        return dayRepository.updateEndHour(id,Time.valueOf(hour))
    }

    fun deleteDay(day: Day){
        dayRepository.delete(day)
    }

}