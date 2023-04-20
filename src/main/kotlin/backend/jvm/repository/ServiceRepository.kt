package backend.jvm.repository

import backend.jvm.model.ServiceDB
import backend.jvm.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date
import java.sql.Time
import java.time.Duration


interface ServiceRepository : JpaRepository<ServiceDB, Int> {

    fun getServiceDBById(id: Int): ServiceDB

    @Query(value = "select * from service s where s.company_id = :id", nativeQuery = true)
    fun getAllServicesFromACompany(@Param("id") id: Int): List<ServiceDB>


    @Query(value = "select count(appointment_id) from service_appointment where service_id=:serviceId and appointment_id=:appointmentId ", nativeQuery = true)
    fun countAppointments(@Param("serviceId") serviceId: Int, @Param("appointmentId") appointmentId: Int): Int

    @Query(value ="select * from sch_user where id in " +
            "(select user_id from user_service where service_id = :idService) " +
            "and id not in (select user_id from unavailability where hour_begin between :hourBegin and :hourEnd and (date_begin <= :date and date_end >= :date)) and service_id in (select id from service where company_id = :companyId);", nativeQuery = true)
    fun getAvailableEmployeesForServiceByDateAndHourAndCompany(@Param("idService") idService:Int, @Param("hourBegin") hourBegin: Time, @Param("hourEnd") hourEnd: Time, @Param("date") date: Date, @Param("companyId") companyId: Int):List<User>

    @Query(value ="update service set price =:price where id=:idService returning price", nativeQuery = true)
    fun updatePrice( @Param("idService") idService: Int, @Param("price") price: Double): Long

    @Query(value ="update service set duration =:duration where id=:idService returning duration  ", nativeQuery = true)
    fun updateDuration( @Param("idService") idService: Int, @Param("duration") duration: Time): Duration

    @Query(value = "update service set number_max =:number where id=:idService returning number_max  ", nativeQuery = true)
    fun updateMaxNumber( @Param("idService") idService: Int, @Param("number") number: Int):Double

}

//9:00:00 11:00:00 -- 12:00:00 13:00:00 unavail

//11:30:00 - 13:30:00

// if in 11:30:00 - 13:30:00 houver um begin hour no unavailable retorna false