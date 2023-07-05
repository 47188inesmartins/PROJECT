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

     @Query(value = "select * from sch_user where id not in " +
             "(select user_id from unavailability where (date_end is null and date_begin =:date" +
             "                                        and ((hour_begin  >= :beginHour and hour_begin < :endHour)" +
             "                                        or (hour_end > :beginHour and hour_end <= :endHour)))" +
             "        or (:date <= date_begin and :date >= date_end))" +
             "and id in (select user_id from user_service where service_id = :id)", nativeQuery = true)
     fun getAvailableEmployeesByService(@Param("id") serviceId: Int, @Param("date") date: Date, @Param("beginHour") beginHour: Time, @Param("endHour") endHour: Time): List<UserEntity>

     @Query(value = "update SCH_USER set password = :pass where id = :id returning name", nativeQuery = true)
     fun changePassword (@Param("pass") pass: String, @Param("id") id: Int): String

     @Query(value = "update USER_ROLE set role_name= :nameRole where user_id=:id returning role_name", nativeQuery = true)
     fun changeRole (@Param("id") id: Int, @Param("nameRole") name: String): String

     @Query(value = "select * from SCH_USER s inner join user_role ur on s.id =:id and ur.role_name =:role ", nativeQuery = true)
     fun getUserDBByIdAndRole(@Param("role") role: String, @Param("id") id: Int): UserEntity?


     @Query(value = "select * from SCH_USER s inner join user_company us on us.company_id = :company " +
             "and us.user_id = s.id and (us.role = 'EMPLOYEE' or us.role = 'MANAGER')", nativeQuery = true)
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
}
