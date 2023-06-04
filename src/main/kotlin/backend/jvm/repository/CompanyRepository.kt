package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time
import java.util.*

interface CompanyRepository : JpaRepository<Company, Int>{

    @Query(name = "getCompanyById")
    fun findAllById(id: Int): Company?

    fun findCompanyByNif(nif:String): Company?

    @Query(value = "select company.name from company inner join schedule s on company.id = s.company_id and s.id = :schedule", nativeQuery = true)
    fun getCompanyBySchedule(@Param("schedule") schedule: Int):String?

    @Query(value = "select * from appointment a where a.schedule_id = (select id from schedule s where s.company_id = :id) and a.app_date = :date and a.app_hour = :hour", nativeQuery = true)
    fun getAppointmentsByDateAndHour(@Param("id") id: Int, @Param("date") date: Date, @Param("hour") time: Time): List<Appointment>

    @Query(value = "select * from vacation v where v.schedule_id = (select id from schedule s where s.company_id = :id)", nativeQuery = true)
    fun getVacation(@Param("id") id: Int): List <Vacation>

    @Query(value = "update company set address=:address where id = :id returning id", nativeQuery = true)
    fun changeAddress(@Param("id") id: Int, @Param("address") address: String)

    @Query(value = "update company c set c.description=:description where c.id = :id", nativeQuery = true)
    fun changeDescription(@Param("id") id: Int, @Param("description") description: String): Company

    @Query(value = "select c.id,c.nif,c.address,c.name,c.type,c.description " +
            "from COMPANY C inner join USER_COMPANY UC on uc.company_id = c.id " +
            "and uc.user_id = 1 and uc.role = 'EMPLOYEE'",nativeQuery = true)
    fun getCompanyByUserIdAndRole(@Param("user_id") user: Int, @Param("role")role: String): List<Company>
}
