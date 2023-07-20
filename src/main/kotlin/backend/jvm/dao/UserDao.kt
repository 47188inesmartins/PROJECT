package backend.jvm.dao

import backend.jvm.model.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.sql.Time
import java.util.UUID

interface UserDao: JpaRepository<UserEntity, Int> {

     @Query(name = "getUserById")
     fun getUserById(@Param("id") id: Int): UserEntity?

     @Query(name = "getUserByToken")
     fun getUserByToken(token: UUID): UserEntity?

     fun getUsersByEmail (email: String): UserEntity?

     @Query(value = "SELECT * FROM sch_user u " +
             "WHERE u.id IN (" +
             "   SELECT us.user_id FROM user_service us " +
             "   WHERE us.service_id = :serviceId " +
             ") " +
             "AND u.id NOT IN (" +
             "   SELECT ua.user_id FROM unavailability ua " +
             "   WHERE (ua.date_end IS NULL AND ua.date_begin = :date " +
             "       AND ((ua.hour_begin >= :beginHour AND ua.hour_begin < :endHour) " +
             "       OR (ua.hour_end > :beginHour AND ua.hour_end <= :endHour))) " +
             "       OR (:date BETWEEN ua.date_begin AND ua.date_end)" +
             ")", nativeQuery = true)
     fun getAvailableEmployeesByService(
          @Param("serviceId") serviceId: Int,
          @Param("date") date: Date,
          @Param("beginHour") beginHour: Time,
          @Param("endHour") endHour: Time
     ): List<UserEntity>

     @Query(value = "update SCH_USER set password = :pass where id = :id returning name", nativeQuery = true)
     fun changePassword (@Param("pass") pass: String, @Param("id") id: Int): String

     @Query(value = "update USER_ROLE set role_name= :nameRole where user_id=:id returning role_name", nativeQuery = true)
     fun changeRole (@Param("id") id: Int, @Param("nameRole") name: String): String

     @Query(value = "select * from SCH_USER s inner join user_company uc on uc.company_id =:companyId and uc.role =:role and s.id = :userId", nativeQuery = true)
     fun getUserDBByIdAndRole(@Param("role") role: String, @Param("companyId") companyId: Int, @Param("userId") userId: Int): UserEntity?


     @Query(value = "select * from SCH_USER s inner join user_company us on us.company_id = :company " +
             "and us.user_id = s.id and (us.role = 'EMPLOYEE')", nativeQuery = true)
     fun getUsersEmployeesByCompany(@Param("company") company: Int): List<UserEntity>?

     @Query(value = "(select uc.role from sch_user u inner join user_company uc on (u.id = :user and uc.user_id =u.id  and uc.company_id = :company " +
             "and (uc.role = 'EMPLOYEE' or uc.role = 'MANAGER')))", nativeQuery = true)
     fun getUserRoleByCompany(@Param("user") user: Int, @Param("company") company: Int): String?

     @Modifying
     @Query(value = "update sch_user set profile_pic = :pic where id = :id ", nativeQuery = true)
     @Transactional
     fun updateUserPicture(@Param("id") id: Int, @Param("pic") pic: ByteArray)

     @Modifying
     @Query(value = "update sch_user set status = 'VALIDATED' where email = :email", nativeQuery = true)
     @Transactional
     fun changeStatusByEmail(@Param("email") email: String)

     @Modifying
     @Query(value = "delete * from user_service where user_id = :user_id and service_id in (select s.id from service s where company_id = :company_id)", nativeQuery = true)
     @Transactional
     fun deleteUserServices(@Param("user_id") user_id: Int, @Param("company_id") company_id: Int)
}
