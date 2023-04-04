package backend.jvm.services

import backend.jvm.model.Day
import backend.jvm.repository.DayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time

@Service
class DayServices {


    @Autowired
    lateinit var dayRepository:DayRepository

    fun addOpenDay(day: Day):Day{
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