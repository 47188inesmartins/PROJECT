package backend.jvm.services.interfaces

import backend.jvm.model.Role

interface IRoleServices {
    fun addRole(role: Role): Role

    fun deleteRole(id: Int): Boolean
}