package backend.jvm.repository

import backend.jvm.model.UserDB
import backend.jvm.services.dto.URoles
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date
import java.sql.Time
import java.util.UUID

interface UserRepository: JpaRepository<UserDB, Int> {

     @Query(name = "getUserById")
     fun getUserById(@Param("id") id: Int): UserDB?

     @Query(name = "getUserByToken")
     fun getUserByToken(token: UUID): UserDB?

     fun getUsersByEmail (email: String): UserDB?

     @Query(value = "select * from sch_user where id not in " +
             "(select user_id from unavailability where (date_end is null and date_begin =:date" +
             "                                        and ((hour_begin  >= :beginHour and hour_begin < :endHour)" +
             "                                        or (hour_end > :beginHour and hour_end <= :endHour)))" +
             "        or (:date <= date_begin and :date >= date_end))" +
             "and id in (select user_id from user_service where service_id = :id)", nativeQuery = true)
     fun getAvailableEmployeesByService(@Param("id") serviceId: Int, @Param("date") date: Date, @Param("beginHour") beginHour: Time, @Param("endHour") endHour: Time): List<UserDB>


     @Query(value = "select role_name from user_role r where r.user_id = :id", nativeQuery = true)
     fun getRole (@Param("id") id: Int): String?

     @Query(value = "select company_id, role from user_company uc where uc.user_id = :id", nativeQuery = true)
     fun getRoles (@Param("id") id: Int): List<URoles>?

     @Query(value = "update SCH_USER set password = :pass where id = :id returning name", nativeQuery = true)
     fun changePassword (@Param("pass") pass: String, @Param("id") id: Int): String

     @Query(value = "update SCH_USER set availability = :availability, max_number_people = :number where id = :id returning availability", nativeQuery = true)
     fun changeAvailabilityAndMaxNumber (@Param("availability") availability: String,@Param("number") number: Int, @Param("id") id: Int): String

     @Query(value = "update USER_ROLE set role_name= :nameRole where user_id=:id returning role_name", nativeQuery = true)
     fun changeRole (@Param("id") id: Int, @Param("nameRole") name: String): String

     @Query(value = "select user_id from unavailability where user_id= :id", nativeQuery = true)
     fun getUnavailableDays(@Param("id")id: Int): List<Int>

     @Query(value = "select * from SCH_USER where email = :email and password = :pass", nativeQuery = true)
     fun getUsersByEmailPass(@Param("pass") pass: String, @Param("email") email: String): UserDB?

     @Query(value ="select * from sch_user where id in " +
             "(select user_id from user_service where service_id = :idService) " +
             "and id not in (select user_id from unavailability where hour_begin between :hourBegin and :hourEnd and (date_begin <= :date and date_end >= :date))", nativeQuery = true)
     fun getAvailableEmployeesForServiceByDateAndHourAndCompany(@Param("idService") idService:Int,
                                                                @Param("hourBegin") hourBegin: Time,
                                                                @Param("hourEnd") hourEnd: Time,
                                                                @Param("date") date: Date
          /*, @Param("companyId") companyId: Int*/):List<UserDB>

     @Query(value = "select * from SCH_USER s inner join user_role ur on s.id =:id and ur.role_name =:role ", nativeQuery = true)
     fun getUserDBByIdAndRole(@Param("role") role: String, @Param("id") id: Int): UserDB?


     @Query(value = "select * from SCH_USER s inner join user_company us on us.company_id = :company " +
             "and us.user_id = s.id and us.role = 'EMPLOYEE'", nativeQuery = true)
     fun getUserEmployeesByCompany(@Param("company") company: Int): List<UserDB>?
}
