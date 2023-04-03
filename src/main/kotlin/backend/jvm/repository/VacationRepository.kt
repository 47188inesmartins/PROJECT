package backend.jvm.repository

import backend.jvm.model.Vacation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Date


interface VacationRepository: JpaRepository<Vacation, Int>{

    @Query(value ="update vacation set date_begin =:date where id = :id", nativeQuery = true)
    fun changeBeginDate(id:Int,date:Date):Vacation

    @Query(value ="update vacation set date_end =:date where id = :id", nativeQuery = true)
    fun changeEndDate(id:Int,date:Date):Vacation
}