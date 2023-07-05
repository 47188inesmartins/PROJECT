package backend.jvm.repository

//import backend.jvm.model.Day
import backend.jvm.model.Day
import backend.jvm.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time

interface DayRepository : JpaRepository<Day, Int> {

    @Query(name = "getDayById")
    fun getDayById(@Param("id")id: Int): Day?


    @Query(value = "select d.id, d.begin_hour, d.end_hour, d.interval_begin, d.interval_end, week_days, schedule_id from sch_day d inner join schedule s on d.schedule_id = s.id and s.company_id = :id", nativeQuery = true)
    fun getOpenDays(@Param("id") id: Int): List<Day>

    @Query(value = "update sch_day set begin_hour =:hour where id = :id returning begin_hour", nativeQuery = true)
    fun updateBeginHour(@Param("id") id: Int, @Param("hour") hour: Time): Time

    @Query(value = "update sch_day set end_hour =:hour where id = :id returning end_hour", nativeQuery = true)
    fun updateEndHour(@Param("id")id: Int, @Param("hour") hour: Time): Time

    @Query(value = "update sch_day set day_interval =:hour where id = :id returning day_interval", nativeQuery = true)
    fun updateInterval(@Param("id")id: Int, @Param("hour") hour: Time): Time

    @Query(value = "select * from sch_day where schedule_id =:schedule_id", nativeQuery = true)
    fun getDayByScheduleId(@Param("schedule_id") schedule_id: Int): List<Day>

    @Query(value = "select * from sch_day where service_id = :service_id", nativeQuery = true)
    fun getAvailableServicesDay(@Param("service_id") service_id: Int): List<Day>

    @Query(value = "select * from sch_day where week_days = :weekDays and schedule_id = :schedule_id", nativeQuery = true)
    fun getDayByScheduleIdAndWeekDays(@Param("schedule_id") schedule_id: Int, @Param("weekDays") weekDays: String):List<Day>

    fun getDayByWeekDaysAndSchedule(weekDays: String, schedule_id: Schedule): Day

    @Query(value = "select sd.id,sd.begin_hour,sd.end_hour,sd.interval_begin,sd.interval_end,sd.week_days,sd.schedule_id" +
            " from sch_day sd on sd.week_days = :weekDays inner join service_day s " +
            "on s.service_id = :service_id and sd.id = s.day_id" , nativeQuery = true)
    fun getDayByServiceAndWeek(@Param("service_id") service_id: Int, @Param("weekDays") weekDays: String):Day

}