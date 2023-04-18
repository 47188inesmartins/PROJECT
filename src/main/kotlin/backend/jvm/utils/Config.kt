package backend.jvm.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class AppConfig :
    WebMvcConfigurer {

    @Autowired
    lateinit var authenticationInterceptor: AuthenticationInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns(listOf(
                "/appointment",
                "/company",
                "/vacation",
                "/schedule",
                "/service"
            ))
    }

}