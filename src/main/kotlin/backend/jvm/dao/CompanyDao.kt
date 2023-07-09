package backend.jvm.dao

import backend.jvm.model.*
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.company.CompanyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time
import java.util.*

interface CompanyDao : JpaRepository<CompanyEntity, Int>{

    @Query(value = "SELECT * FROM COMPANY c WHERE type = ANY(:categories)", nativeQuery = true)
    fun getCompaniesByCategory(@Param("categories") categories: Array<String>): List<CompanyEntity>?

    @Query(name = "getCompanyById")
    fun findAllById(id: Int): CompanyEntity?

    @Query(value = "Select * from company c where c.name ilike :search or c.description ilike :search or c.type ilike :search", nativeQuery = true)
    fun getCompanyBySearch(@Param("search") search: String):List<CompanyEntity>?

    fun findCompanyByNif(nif:String): CompanyEntity?

    @Query(value = "select company.name from company inner join schedule s on company.id = s.company_id and s.id = :schedule", nativeQuery = true)
    fun getCompanyBySchedule(@Param("schedule") schedule: Int):String?

    @Query(value = "select * from appointment a where a.schedule_id = (select id from schedule s where s.company_id = :id) and a.app_date = :date and a.app_hour = :hour", nativeQuery = true)
    fun getAppointmentsByDateAndHour(@Param("id") id: Int, @Param("date") date: Date, @Param("hour") time: Time): List<AppointmentEntity>

    @Query(value = "update company set address=:address where id = :id returning id", nativeQuery = true)
    fun changeAddress(@Param("id") id: Int, @Param("address") address: String)

    @Query(value = "update company c set c.description=:description where c.id = :id", nativeQuery = true)
    fun changeDescription(@Param("id") id: Int, @Param("description") description: String): CompanyEntity


    @Query(value = "select c.id, c.nif, c.address, c.latitude, c.longitude, c.name, c.phone, c.type, c.description " +
            "from COMPANY C inner join USER_COMPANY UC on uc.company_id = c.id " +
            "and uc.user_id = :user_id and uc.role = :role",nativeQuery = true)
    fun getCompanyByUserIdAndRole(@Param("user_id") user: Int, @Param("role") role: String): List<CompanyEntity>
}
