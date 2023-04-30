package backend.jvm.utils.pipeline

import backend.jvm.model.UserDB
import backend.jvm.services.UserServices
import org.springframework.stereotype.Component

@Component
class AuthorizationHeaderProcessor(
    val userServices: UserServices
) {

    fun process(credentials : String?) : Pair <UserDB?, String>?{
        if(credentials == null) return null
        val parts = credentials.trim().split(" ")
        if(parts.size != 2 || parts[0].lowercase() != SCHEMA) return Pair(null,"GUEST")
        return Pair (userServices.getUserByToken(parts[1]),userServices.getRoleByToken(parts[1])?:"GUEST")
    }

    companion object{
        const val SCHEMA = "bearer"
    }
}