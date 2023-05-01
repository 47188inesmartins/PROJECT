package backend.jvm.services.interfaces

import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.DayOutputDto
import java.sql.Time

interface IDayServices {
    fun addOpenDay(day: DayInputDto): DayOutputDto
    fun updateBeginHour(id:Int,hour: String): Time
    fun updateEndHour(id:Int,hour: String): Time
    fun deleteDay(day: Int)
    fun getAvailableServicesDays(serviceId: Int): List<DayOutputDto>
}