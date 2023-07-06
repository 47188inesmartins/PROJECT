package backend.jvm.dao

import backend.jvm.model.vacation.VacationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date


interface VacationDao: JpaRepository<VacationEntity, Int>{

    @Query(value ="update vacation set date_begin =:date where id = :id returning date_begin", nativeQuery = true)
    fun changeBeginDate(@Param("id") id:Int, @Param("date") date:Date):Date

    @Query(value ="update vacation set date_end =:date where id = :id returning date_end", nativeQuery = true)
    fun changeEndDate( @Param("id") id:Int, @Param("date") date:Date):Date

    @Query(value ="select * from vacation v where v.schedule_id = :schedule", nativeQuery = true)
    fun getVacationsByScheduleId(@Param("schedule")schedule: Int):List<VacationEntity>

}