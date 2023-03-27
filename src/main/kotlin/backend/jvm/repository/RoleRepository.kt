package backend.jvm.repository

import org.springframework.data.jpa.repository.JpaRepository
import javax.management.relation.Role

interface RoleRepository : JpaRepository<Role, Int>{

}