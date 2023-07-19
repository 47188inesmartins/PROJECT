package backend.jvm.services.interfaces

import backend.jvm.model.day.DayEntity
import backend.jvm.model.day.DayInputDto
import backend.jvm.model.day.DayOutputDto
import java.sql.Time

interface IDayServices {

    fun addOpenDay(day: DayInputDto): DayOutputDto

    fun addSpecialDayByService(day: DayInputDto, serviceId: Int, companyId: Int): DayOutputDto

    fun getScheduleByWeekDay(weekDay: String, cid: Int): List<String>

    fun addOpenDays(day: List<DayInputDto>, companyId: Int, interval: String?): List<DayEntity>

    fun updateBeginHour(id:Int,hour: String): Time

    fun updateEndHour(id:Int,hour: String): Time

    fun deleteDay(day: Int)

    fun getAvailableDaysByService(serviceId: Int): List<DayOutputDto>

}