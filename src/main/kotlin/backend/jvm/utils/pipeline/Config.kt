package backend.jvm.utils.pipeline

import backend.jvm.utils.pipeline.AuthenticationsInterceptor
import backend.jvm.utils.pipeline.RoleInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.util.AntPathMatcher
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AppConfig :
    WebMvcConfigurer {

    @Autowired
    lateinit var authenticationsInterceptor: AuthenticationsInterceptor
    @Autowired
    lateinit var roleInterceptor: RoleInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        val pathMatcher = AntPathMatcher()
        registry.addInterceptor(authenticationsInterceptor)
            .addPathPatterns(listOf(
                "/appointment/**",
                "/company/**",
                "/vacation/**",
                "/schedule/**",
                "/service/**",
                "/user/**"
            ))
            .pathMatcher(pathMatcher)

        registry.addInterceptor(roleInterceptor)
    }
}