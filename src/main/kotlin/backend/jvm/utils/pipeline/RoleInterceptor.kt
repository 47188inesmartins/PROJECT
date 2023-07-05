package backend.jvm.utils.pipeline

import backend.jvm.dao.UserCompanyDao
import backend.jvm.services.UserServices
import backend.jvm.utils.UserRoles
import backend.jvm.utils.errorHandling.InvalidUser
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView


@Component
class RoleInterceptor (
    val userServices: UserServices,
    val userCompanyDao: UserCompanyDao
) : HandlerInterceptor {

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        if(handler is HandlerMethod) {
            if (request.requestURI.equals("/company") && request.method.equals("POST")) {
                if (response.status == HttpStatus.CREATED.value()) {
                    val headerAuth =  request.getHeader("Authorization").substringAfter("Bearer ")
                    val user = userServices.getUserByToken(headerAuth) ?: throw InvalidUser()
                    userServices.changeRole(user.id, UserRoles.MANAGER.name)
                }
            }
        }
    }
}