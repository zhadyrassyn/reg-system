package kz.edu.sdu.regsystem.stand.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthInterceptorConfig : WebMvcConfigurer {

    @Autowired
    lateinit var moderatorAuthInterceptor: ModeratorAuthInterceptor

    @Autowired
    lateinit var studentAuthInterceptor: StudentAuthInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(moderatorAuthInterceptor).addPathPatterns("/moderator/**")
        registry.addInterceptor(studentAuthInterceptor).addPathPatterns("/student/**")
    }
}