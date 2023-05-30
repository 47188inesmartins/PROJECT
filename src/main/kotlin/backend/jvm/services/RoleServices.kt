package backend.jvm.services

import backend.jvm.model.Role
import backend.jvm.repository.RoleRepository
import backend.jvm.services.interfaces.IRoleServices
import backend.jvm.utils.UserRoles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleServices : IRoleServices {

    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun addRole(role: Role): Role {
        UserRoles.valueOf(role.name.uppercase(Locale.getDefault()))
        return roleRepository.save(role)
    }

    override fun deleteRole(id: Int): Boolean {
        roleRepository.deleteById(id)
        return true
    }

    fun getRoleByToken(token: String): String {
        val uuid = UUID.fromString(token)
        return roleRepository.getRoleByToken(uuid)
    }

}