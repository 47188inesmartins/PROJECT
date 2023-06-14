package backend.jvm.services

import backend.jvm.repository.CompanyRepository
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

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
        val service = day.service?.map { index -> serviceRepository.getServiceDBById(index) ?: throw InvalidOpenDay() }
        val dayDb = day.mapToDayDb(day,schedule,service)
        val db = dayRepository.save(dayDb)
        return  DayOutputDto(db)
    }


   fun getScheduleByWeekDay(weekDay: String, cid: Int): List<String> {
       val schedule = scheduleRepository.getScheduleByCompany_Id(cid) ?: throw CompanyNotFound()
       val days = dayRepository.getDayByScheduleIdAndWeekDays(schedule.id, weekDay)

       if(days.isEmpty()) return emptyList()

       val startTime = days[0].beginHour
       val endTime = days[0].endHour
       val interval = schedule.betweenInterval
       var currentTime = startTime

       val hoursList = mutableListOf<String>()

       while (currentTime.before(endTime) || currentTime == endTime) {
           hoursList.add(currentTime.toString().substring(0..4))
           currentTime = getEndHour(currentTime, interval!!)
       }

       return hoursList
    }

    fun getEndHour(tempo1: Time, tempo2: Time): Time {
        val additionalTime = tempo2.time - tempo2.timezoneOffset * 60 * 1000
        println(Time(tempo1.time + additionalTime))
        return Time(tempo1.time + additionalTime)
    }



   fun addOpenDays(day: List<DayInputDto>, companyId: Int, interval: String) {
        companyRepository.findAllById(companyId) ?: throw InvalidSchedule()
        val schedule = scheduleRepository.getScheduleByCompany_Id(companyId) ?: throw InvalidSchedule()
        val intervalBetween = Time.valueOf(interval.plus(":00"))
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

    /*
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
    */


    fun getDayOfWeekFromDate(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        val dayOfWeek = date.dayOfWeek
        val locale = Locale.getDefault()
        return dayOfWeek.getDisplayName(TextStyle.FULL, locale).substring(3).toUpperCase()
    }


}