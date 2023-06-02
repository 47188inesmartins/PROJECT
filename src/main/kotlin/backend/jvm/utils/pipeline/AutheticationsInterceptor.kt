package backend.jvm.utils.pipeline

import backend.jvm.utils.RoleManager
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthenticationsInterceptor(
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
                println(roleAllowed)
                if(roleAllowed == null){
                    response.status = 200
                    return true
                }
                return if(user?.second in roleAllowed.roleList){
                    println("200")
                    response.status = 200
                    true
                } else{
                    println("401")
                    response.status = 401
                    false
                }
            }
        }
        return true
    }


}


