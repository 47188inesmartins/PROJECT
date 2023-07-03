package backend.jvm.services.interfaces

import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.DayOutputDto
import java.sql.Time

interface IDayServices {

    fun addOpenDay(day: DayInputDto): DayOutputDto

    fun addSpecialDayByService(day: DayInputDto, serviceId: Int, companyId: Int):DayOutputDto

    fun getScheduleByWeekDay(weekDay: String, cid: Int): List<String>

    fun addOpenDays(day: List<DayInputDto>, companyId: Int, interval: String)

    fun updateBeginHour(id:Int,hour: String): Time

    fun updateEndHour(id:Int,hour: String): Time

    fun deleteDay(day: Int)

    fun getAvailableDaysByService(serviceId: Int): List<DayOutputDto>

}