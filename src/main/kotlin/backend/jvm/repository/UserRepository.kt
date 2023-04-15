package backend.jvm.repository

import backend.jvm.model.Company
import backend.jvm.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository: JpaRepository<User, Int> {

     fun getUsersByCompanyId (compId: Company): User

    /* fun getUsersByServices (service: List<Services>): List<User>

     fun getUsersByAppointment (appointment: List<Appointment>): List<User>*/

     fun getUsersByEmail (email: String): User
     @Query(value = "select availability from SCH_USER u " +
             "inner join u_role r on r.id = :id and r.name = 'employee'", nativeQuery = true)
     fun getEmployeeAvailability(@Param("id") id: Int)

     @Query(value = "select name from u_role r where r.user_id = :id", nativeQuery = true)
     fun getRole (@Param("id") id: Int): String?

     @Query(value = "update SCH_USER set password = :pass where id = :id", nativeQuery = true)
     fun changePassword (@Param("pass") pass: String, @Param("id") id: Int): User

     @Query(value = "update SCH_USER set availability = :availability where id = :id", nativeQuery = true)
     fun changeAvailability (@Param("availability") availability: String, @Param("id") id: Int): User

     @Query(value = "update U_ROLE set name= :nameRole where id=:id", nativeQuery = true)
     fun changeRole (@Param("id") id: Int, @Param("nameRole") name: String): User
}