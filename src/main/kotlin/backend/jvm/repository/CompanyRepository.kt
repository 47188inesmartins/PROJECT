package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time
import java.util.*

interface CompanyRepository : JpaRepository<Company, Int>{

    fun findAllById(id: Int): Company
    fun findCompanyByNif(nif:String): Company

    @Query(value = "select * from service s where s.cid = :id")
    fun getAllServices(@Param("id") id: Int): List<Services>

    @Query(value = "select * from employee e where e.comp_id = :id")
    fun getAllEmployees(@Param("id") id: Int): List<Employee>

    @Query(value = "select * from appointment a where a.sid = (select id from schedule s where s.comp_id = :id)")
    fun getAllAppointments(@Param("id") id: Int): List<Appointment>

    @Query(value = "select * from appointment a where a.sid = (select id from schedule s where s.comp_id = :id) and a.app_date = :date and a.app_hour = :time")
    fun getAppointment(@Param("id") id: Int, @Param("date") date: Date, @Param("hour") time: Time): Appointment

    @Query(value = "select * from sch_day d where d.sid = (select id from schedule s where s.comp_id = :id)")
    fun getOpenDays(@Param("id") id: Int): List <Day>

    @Query(value = "select * from vacation v where v.sid = (select id from schedule s where s.comp_id = :id)")
    fun getVacation(@Param("id") id: Int): List <Vacation>

    @Query(value = "update company c set c.address=:address where c.id = :id")
    fun changeAddress(@Param("id") id: Int, @Param("address") address: String): Company

    @Query(value = "update company c set c.description=:description where c.id = :id")
    fun changeDescription(@Param("id") id: Int, @Param("description") description: String): Company

}
