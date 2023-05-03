package backend.jvm.services.interfaces

import backend.jvm.services.dto.DayOutputDto
import backend.jvm.services.dto.ScheduleInputDto
import backend.jvm.services.dto.ScheduleOutputDto
import backend.jvm.services.dto.VacationOutputDto

interface IScheduleServices {
    fun addSchedule(schedule: ScheduleInputDto): ScheduleOutputDto

    fun deleteSchedule(id: Int)

    fun getSchedule(id:Int): ScheduleOutputDto?

    fun getOpenDays(id: Int): List<DayOutputDto>

    fun getVacation(id: Int): List<VacationOutputDto>
}