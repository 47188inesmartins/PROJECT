package backend.jvm.utils.pipeline

import backend.jvm.services.UserServices
import org.springframework.stereotype.Component

@Component
class AuthorizationHeaderProcessor(
    val userServices: UserServices
) {

    fun process(credentials: String?, companyId: Int?): String? {
        if (credentials == null) return null
        val parts = credentials.trim().split(" ")
        if (parts.size != 2 || parts[0].lowercase() != SCHEMA) return null
        if(companyId == null) return "CLIENT"
        return userServices.getRoleByUserIdAndCompany(companyId, parts[1]) ?: return "CLIENT"
    }

    companion object{
        const val SCHEMA = "bearer"
    }
}