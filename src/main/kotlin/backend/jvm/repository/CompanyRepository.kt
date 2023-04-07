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

    @Query(value = "from service s where s.cid = :id", nativeQuery = true)
    fun getAllServices(@Param("id") id: Int): List<Services>

  /*  @Query(value = "from employee e where e.comp_id = :id")
    fun getAllEmployees(@Param("id") id: Int): List<Employee>
*/
    @Query(value = "from appointment a where a.sid = (select id from schedule s where s.comp_id = :id)", nativeQuery = true)
    fun getAllAppointments(@Param("id") id: Int): List<Appointment>

    @Query(value = "from appointment a where a.sid = (select id from schedule s where s.comp_id = :id) and a.app_date = :date and a.app_hour = :time", nativeQuery = true)
    fun getAppointment(@Param("id") id: Int, @Param("date") date: Date, @Param("time") time: Time): Appointment

    @Query(value = "from sch_day d where d.sid = (select id from schedule s where s.comp_id = :id)", nativeQuery = true)
    fun getOpenDays(@Param("id") id: Int): List <Day>

    @Query(value = "from vacation v where v.sid = (select id from schedule s where s.comp_id = :id)", nativeQuery = true)
    fun getVacation(@Param("id") id: Int): List <Vacation>

    @Query(value = "update company c set c.address=:address where c.id = :id", nativeQuery = true)
    fun changeAddress(@Param("id") id: Int, @Param("address") address: String): Company

    @Query(value = "update company c set c.description=:description where c.id = :id", nativeQuery = true)
    fun changeDescription(@Param("id") id: Int, @Param("description") description: String): Company

}
