package backend.jvm.services.interfaces

import backend.jvm.model.schedule.ScheduleInputDto
import backend.jvm.model.schedule.ScheduleOutputDto

interface IScheduleServices {
    fun addSchedule(schedule: ScheduleInputDto): ScheduleOutputDto

    fun deleteSchedule(id: Int)

    fun getScheduleById(id:Int): ScheduleOutputDto?

  /*  fun getOpenDays(id: Int): List<DayOutputDto>

    fun getVacation(id: Int): List<VacationOutputDto>**************/
}