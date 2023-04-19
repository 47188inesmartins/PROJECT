package backend.jvm.utils

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthenticationInterceptor(
    private val authorizationHeaderProcessor : AuthorizationHeaderProcessor
)  : HandlerInterceptor {
    private val NAME_AUTHORIZATION_HEADER = "Authorization"
    private val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if(handler is HandlerMethod ){

            val user = authorizationHeaderProcessor.process(request.getHeader(NAME_AUTHORIZATION_HEADER))

            return if(user != null && user.first == null){
                response.status = 401
                response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, AuthorizationHeaderProcessor.SCHEMA)
                false
            }else{
                val roleAllowed = handler.getMethodAnnotation(RoleManager::class.java)
                if(roleAllowed == null){
                    response.status = 200
                    return true
                }
                println("roles ====" + roleAllowed.roleList[0] + user?.second)
                return if(user?.second in roleAllowed.roleList){

                    response.status = 200
                    true

                } else{
                    response.status = 401
                    false
                }

                //PlayerArgumentResolver.addPlayerTo(player,request)
                true
            }

        }
        return true
    }
}


