package backend.jvm.services

import backend.jvm.repository.DayRepository
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.DayOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.interfaces.IDayServices
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time

@Service
class DayServices : IDayServices {
    companion object{
        val listDayOfWeek = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
    }

    @Autowired
    lateinit var dayRepository:DayRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var serviceRepository: ServiceRepository

    override fun addOpenDay(day: DayInputDto):DayOutputDto {
        if(!listDayOfWeek.contains(day.weekDays.uppercase())) throw InvalidOpenDay()
        val schedule = if(day.schedule != null) scheduleRepository.getReferenceById(day.schedule) else throw InvalidSchedule()
        val service = day.service?.let { serviceRepository.findById(it).get() } ?: throw InvalidOpenDay()
        val dayDb = day.mapToDayDb(day,schedule,service)
        val db = dayRepository.save(dayDb)
        return  DayOutputDto(db)
    }

    override fun updateBeginHour(id:Int,hour: String): Time {
        if(dayRepository.getDayById(id) == null ) throw InvalidDay()
        return dayRepository.updateBeginHour(id, Time.valueOf(hour))
    }

    override fun updateEndHour(id:Int,hour: String): Time {
        if(dayRepository.getDayById(id) == null ) throw InvalidDay()
        return dayRepository.updateEndHour(id,Time.valueOf(hour))
    }

    override fun deleteDay(day: Int){
        dayRepository.deleteById(day)
    }

    override fun getAvailableDaysByService(serviceId: Int): List<DayOutputDto> {
        val availableDays = dayRepository.getAvailableServicesDay(serviceId)
        if(availableDays.isEmpty()) throw NoAvailableDays()
        return availableDays.map { DayOutputDto(it) }
    }

    fun getAvailableServiceByWeekDay(weekDay: String): List<ServiceOutputDto>{
        if(!listDayOfWeek.contains(weekDay)) throw InvalidDay()
        val getDay = dayRepository.getDayByWeekDays(weekDay).map { it.service }
        val availableServices = mutableListOf<ServiceOutputDto>()
        getDay.forEach {
            if(it!= null) availableServices.add(ServiceOutputDto(it))
        }
        if (availableServices.isEmpty()) throw NoAvailableServices(weekDay)
        return availableServices
    }
}