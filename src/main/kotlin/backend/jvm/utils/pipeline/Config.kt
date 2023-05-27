package backend.jvm.utils

import backend.jvm.utils.pipeline.RoleInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AppConfig :
    WebMvcConfigurer {

    companion object{
        val MANAGER = listOf("/appointment", "/company")
        val EMPLOYEE = listOf("/vacation", "/schedule", "/service")
    }

    @Autowired
    lateinit var authenticationInterceptor: AuthenticationInterceptor
    @Autowired
    lateinit var roleInterceptor: RoleInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns(listOf(
                "/appointment",
                "/company",
                "/vacation",
                "/schedule",
                "/service"
            ))
        registry.addInterceptor(roleInterceptor)
    }
}