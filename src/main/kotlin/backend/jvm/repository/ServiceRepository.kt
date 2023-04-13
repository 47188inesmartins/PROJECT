package backend.jvm.repository

import backend.jvm.model.Services
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time



interface ServiceRepository : JpaRepository<Services, Int> {


    @Query(value = "select count(appointment_id) from service_appointment where service_id=:serviceId and appointment_id=:appointmentId ", nativeQuery = true)
    fun countAppointments(@Param("serviceId") serviceId: Int, @Param("appointmentId") appointmentId: Int): Int

   // fun findAllByEmployee(employee: List<Employee>):Services
    @Query(value ="select * from service s " +
            "inner join employee_service e on e.service_id = :idService " +
            "inner join employee p on p.id = e.employee_id", nativeQuery = true)
    fun getEmployeeForService(@Param("idService") idService:Int):Services

    @Query(value ="update service set price =:price where id=:idService returning price", nativeQuery = true)
    fun updatePrice( @Param("idService") idService: Int, @Param("price") price: Double): Long

    @Query(value ="update service set duration =:duration where id=:idService  ", nativeQuery = true)
    fun updateDuration( @Param("idService") idService: Int, @Param("duration") duration: Time):Services

    @Query(value ="update service set number_max =:number where id=:idService  ", nativeQuery = true)
    fun updateMaxNumber( @Param("idService") idService: Int, @Param("number") number: Int):Services

    @Query(value = "update SERVICE_APPOINTMENT set availability =:availability where service_id =:idService and appointment_id=:idApp returning availability", nativeQuery = true)
    fun updateAvailability( @Param("idService") idService: Int,@Param("idApp") idApp: Int, @Param("availability") availability: String): String

/*/* @Query(value ="update service set number_max =:number where id=:idService  ", nativeQuery = true)
fun getServicesByIdAndNumberMax(id: Int, numberMax: Int): Services*/*/

    fun getServicesById(id: Int):Services
}