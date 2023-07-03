package backend.jvm.services

import backend.jvm.repository.*
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.DayOutputDto
import backend.jvm.services.interfaces.IDayServices
import backend.jvm.utils.errorHandling.*
import backend.jvm.utils.time.addTimes
import backend.jvm.utils.time.getDayOfWeek
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
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
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var serviceRepository: ServiceRepository

    override fun addOpenDay(day: DayInputDto):DayOutputDto {
        if(!listDayOfWeek.contains(day.weekDays.uppercase())) throw InvalidOpenDay()
        val schedule = if(day.schedule != null) scheduleRepository.getReferenceById(day.schedule) else throw InvalidSchedule()
        val dayDb = day.mapToDayDb(day,schedule,null)
        val db = dayRepository.save(dayDb)
        return  DayOutputDto(db)
    }

    override fun addSpecialDayByService(day: DayInputDto, serviceId: Int, companyId: Int):DayOutputDto {
        if(!listDayOfWeek.contains(day.weekDays.uppercase())) throw InvalidOpenDay()
        val service = serviceRepository.getServiceDBById(serviceId) ?: throw InvalidService()
        val dayDb = day.mapToDayDb(day,null,listOf(service))
        val db = dayRepository.save(dayDb)
        return  DayOutputDto(db)
    }

    override fun getScheduleByWeekDay(weekDay: String, cid: Int): List<String> {
        val schedule = scheduleRepository.getScheduleByCompany_Id(cid) ?: throw CompanyNotFound()
        val days = dayRepository.getDayByScheduleIdAndWeekDays(schedule.id, getDayOfWeek(Date.valueOf(weekDay)))

        if(days.isEmpty()) return emptyList()

        val startTime = days[0].beginHour
        val endTime = days[0].endHour
        val interval = schedule.betweenInterval
        var currentTime = startTime

        val hoursList = mutableListOf<String>()

        while (currentTime.before(endTime) || currentTime == endTime) {
            hoursList.add(currentTime.toString().substring(0..4))
            currentTime = addTimes(currentTime, interval!!)
        }

        return hoursList
    }

    override fun addOpenDays(day: List<DayInputDto>, companyId: Int, interval: String) {
        companyRepository.findAllById(companyId) ?: throw InvalidSchedule()
        val schedule = scheduleRepository.getScheduleByCompany_Id(companyId) ?: throw InvalidSchedule()
        val intervalBetween = Time.valueOf(interval)
        scheduleRepository.updateBetweenInterval(schedule.id, intervalBetween)
        val daysDb = day.map { it.mapToDayDb(it, schedule, null) }
        daysDb.forEach { dayRepository.save(it) }
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

}