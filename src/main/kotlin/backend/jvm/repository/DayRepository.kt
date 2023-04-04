package backend.jvm.repository

//import backend.jvm.model.Day
import backend.jvm.model.Day
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Time

interface DayRepository : JpaRepository<Day, Int> {
    @Query(value = "update sch_day set begin_hour =:hour where id = :id", nativeQuery = true)
    fun updateBeginHour(id: Int,hour: Time): Day

    @Query(value = "update sch_day set end_hour =:hour where id = :id", nativeQuery = true)
    fun updateEndHour(id: Int,hour: Time): Day

    @Query(value = "update sch_day set day_interval =:hour where id = :id", nativeQuery = true)
    fun updateInterval(id: Int,hour: Time): Day

}