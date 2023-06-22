package backend.jvm.repository

import backend.jvm.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID


interface RoleRepository : JpaRepository<Role, Int>{

    fun getRoleByName(name:String):Role

    @Query(value = "select UR.role_name from sch_user inner join USER_ROLE UR on SCH_USER.id = UR.user_id and SCH_USER.token =:token", nativeQuery = true)
    fun getRoleByToken(@Param("token") token: UUID):String

}