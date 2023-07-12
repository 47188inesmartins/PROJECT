package backend.jvm.services
/*
import backend.jvm.model.Role
import backend.jvm.dao.RoleDao
import backend.jvm.services.interfaces.IRoleServices
import backend.jvm.utils.UserRoles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleServices : IRoleServices {

    @Autowired
    lateinit var roleDao: RoleDao

    override fun addRole(role: Role): Role {
        UserRoles.valueOf(role.name.uppercase(Locale.getDefault()))
        return roleDao.save(role)
    }

    override fun deleteRole(id: Int): Boolean {
        roleDao.deleteById(id)
        return true
    }

    override fun getRoleByToken(token: String): String {
        val uuid = UUID.fromString(token)
        return roleDao.getRoleByToken(uuid)
    }

}*/