package backend.jvm.repository

//import backend.jvm.model.Day
import backend.jvm.model.Day
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time

interface DayRepository : JpaRepository<Day, Int> {
    @Query(value = "update sch_day set begin_hour =:hour where id = :id returning begin_hour", nativeQuery = true)
    fun updateBeginHour(@Param("id") id: Int, @Param("hour") hour: Time): Time

    @Query(value = "update sch_day set end_hour =:hour where id = :id returning end_hour", nativeQuery = true)
    fun updateEndHour(@Param("id")id: Int, @Param("hour") hour: Time): Time

    @Query(value = "update sch_day set day_interval =:hour where id = :id returning day_interval", nativeQuery = true)
    fun updateInterval(@Param("id")id: Int, @Param("hour") hour: Time): Time
}