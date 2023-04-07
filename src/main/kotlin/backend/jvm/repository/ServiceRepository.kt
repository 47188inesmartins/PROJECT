package backend.jvm.repository

import backend.jvm.model.Services
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time



interface ServiceRepository : JpaRepository<Services, Int> {


   // fun findAllByEmployee(employee: List<Employee>):Services
    @Query(value ="select * from services s " +
            "inner join employee_service e on e.service_id = :idService " +
            "inner join employee p on p.id = e.employee_id", nativeQuery = true)
    fun getEmployeeForService(@Param("idService") idService:Int):Services

    @Query(value ="update services set price =:price where id=:idService  ", nativeQuery = true)
    fun updatePrice( @Param("idService") idService: Int, @Param("price") price: Double):Services

    @Query(value ="update services set duration =:duration where id=:idService  ", nativeQuery = true)
    fun updateDuration( @Param("idService") idService: Int, @Param("duration") duration: Time):Services

    @Query(value ="update services set number_max =:number where id=:idService  ", nativeQuery = true)
    fun updateMaxNumber( @Param("idService") idService: Int, @Param("number") number: Int):Services

    fun getServicesByIdAndNumberMax(id: Int, numberMax: Int): Services
}