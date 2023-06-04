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

            val user = authorizationHeaderProcessor.process(request.getHeader(NAME_AUTHORIZATION_HEADER),
                request.getAttribute("id") as Int
            )

            val roleAllowed = handler.getMethodAnnotation(RoleManager::class.java)
            
            if(user == null ){
                if(roleAllowed == null){
                    response.status = 200
                    return true
                }
                response.status = 401
                response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, AuthorizationHeaderProcessor.SCHEMA)
                return false
            }else{
                return if(roleAllowed?.roleList?.contains(user) == true){
                    response.status = 200
                    true
                } else{
                    response.status = 401
                    false
                }
            }
        }
        return true
    }
}


