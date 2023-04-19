package backend.jvm.utils
/*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.hibernate.query.sqm.tree.SqmNode
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

class AuthenticationRole (
        private val authorizationHeaderProcessor : AuthorizationHeaderProcessor
    ):HandlerInterceptor {

    companion object{
        val NAME_AUTHORIZATION_HEADER = "Authorization"
       // val EMPLOYEE = "/"
    }


    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean{
        if(handler is HandlerMethod){
            SqmNode.log.info("Aqui")
            val user = authorizationHeaderProcessor.process(request.getHeader(NAME_AUTHORIZATION_HEADER))
            SqmNode.log.info(user)
            return if(user == null){
                response.status = 401
                false
            }else{
                val roleAllowed = handler.getMethodAnnotation(RoleManager::class.java)
                if(roleAllowed == null){
                    response.status = 200
                    return true
                }
                println("roles ====" + roleAllowed.roleList[0] + user.second)
                return if(user.second in roleAllowed.roleList){

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
}*/