package backend.jvm.dao

import backend.jvm.model.service.ServiceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date
import java.sql.Time
import java.time.Duration


interface ServiceDao : JpaRepository<ServiceEntity, Int> {

    @Query(name = "getServiceById")
    fun getServiceDBById(@Param("id") id: Int): ServiceEntity?

    @Query(value = "select * from service s  where s.company_id = :id ", nativeQuery = true)
    fun getAllServicesFromACompany(@Param("id") id: Int): List<ServiceEntity>

    @Query(value ="update service set price =:price where id=:idService returning price", nativeQuery = true)
    fun updatePrice( @Param("idService") idService: Int, @Param("price") price: Double): Long

    @Query(value ="update service set duration =:duration where id=:idService returning duration  ", nativeQuery = true)
    fun updateDuration( @Param("idService") idService: Int, @Param("duration") duration: Time): Duration

    @Query(value = "select sum(s.price) as money from service s  " +
            "inner join USER_SERVICE US on s.id = US.service_id and US.user_id = :userId and s.company_id = :company " +
            "inner join APPOINTMENT_USER AU on AU.user_id = US.user_id " +
            "inner join APPOINTMENT A on A.id = AU.appointment_id " +
            "and A.service_id = s.id and a.app_date>:dateBegin and a.app_date<:dateEnd ", nativeQuery = true)
    fun getEarnedMoneyByEmployee(@Param("userId")userId: Int,@Param("company")company: Int, @Param("dateBegin") dateBegin: Date, @Param("dateEnd") dateEnd: Date): Double?

    @Query(value = "select s.id,s.service_name,s.duration,s.number_max,s.price,s.company_id from service s inner join appointment a on a.service_id = s.id and a.id = :appointment ", nativeQuery = true)
    fun getServiceDBByAppointment(@Param("appointment") appointment: Int): ServiceEntity
}
