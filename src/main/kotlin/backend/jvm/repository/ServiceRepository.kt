package backend.jvm.repository

import backend.jvm.model.Employee
import backend.jvm.model.Services
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Time

interface ServiceRepository : JpaRepository<Services, Int> {


   // fun findAllByEmployee(employee: List<Employee>):Services
    @Query(value ="select * from services s inner join employee_service e on e.service_id = :idService inner join employee p on p.id = e.employee_id", nativeQuery = true)
    fun getEmployeeForService(idService:Int):Services
    @Query(value ="update services set price =:price where id=:idService  ", nativeQuery = true)
    fun updatePrice(idService: Int,price: Double):Services
    @Query(value ="update services set duration =:duration where id=:idService  ", nativeQuery = true)
    fun updateDuration(idService: Int,duration: Time):Services

    @Query(value ="update services set number_max =:number where id=:idService  ", nativeQuery = true)
    fun updateMaxNumber(idService: Int,number: Int):Services

}