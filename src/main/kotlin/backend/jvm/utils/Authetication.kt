package backend.jvm.utils

import backend.jvm.model.User
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

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean{
        if(handler is HandlerMethod && handler.methodParameters.any{ it.parameterType == User::class.java }){
            val player = authorizationHeaderProcessor.process(request.getHeader(NAME_AUTHORIZATION_HEADER))
            return if(player == null){
                response.status = 401
                response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, AuthorizationHeaderProcessor.SCHEMA)
                false
            }else{
                //PlayerArgumentResolver.addPlayerTo(player,request)
                true
            }

        }
        return true
    }

}