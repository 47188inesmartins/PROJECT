package backend.jvm.repository

import backend.jvm.model.Company
import backend.jvm.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository: JpaRepository<User, Int> {


     fun getUserByToken(token: UUID): User?

     fun getUsersByEmailAndPassword(email: String, password: String):User?

    /* fun getUsersByServices (service: List<Services>): List<User>

     fun getUsersByAppointment (appointment: List<Appointment>): List<User>*/

/*     @Query(value = "select name from u_role where user_id = (select id from sch_user where token = :token)")
     fun getRoleByToken(@Param("token") token: UUID): String
*/
     fun getUsersByEmail (email: String): User
     @Query(value = "select availability from SCH_USER u " +
             "inner join u_role r on r.id = :id and r.name = 'employee'", nativeQuery = true)
     fun getEmployeeAvailability(@Param("id") id: Int)

     @Query(value = "select name from u_role r where r.user_id = :id", nativeQuery = true)
     fun getRole (@Param("id") id: Int): String?

     @Query(value = "update SCH_USER set password = :pass where id = :id returning name", nativeQuery = true)
     fun changePassword (@Param("pass") pass: String, @Param("id") id: Int): String

     @Query(value = "update SCH_USER set availability = :availability where id = :id returning name", nativeQuery = true)
     fun changeAvailability (@Param("availability") availability: String, @Param("id") id: Int): String

     @Query(value = "update U_ROLE set name= :nameRole where id=:id returning name", nativeQuery = true)
     fun changeRole (@Param("id") id: Int, @Param("nameRole") name: String): String

     fun getUsersByCompanyId(company_id: Int):List<User>

     @Query(value = "select user_id from unavailability where user_id= :id", nativeQuery = true)
     fun getUnavailableDays(@Param("id")id: Int): List<Int>

     @Query(value = "select * from SCH_USER where email = :email and password = :pass", nativeQuery = true)
     fun getUsersByEmailPass(@Param("pass") pass: String, @Param("email") email: String): User?

   //  fun getRoleByToken()
}