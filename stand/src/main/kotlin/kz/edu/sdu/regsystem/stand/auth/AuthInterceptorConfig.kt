package kz.edu.sdu.regsystem.stand.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class AuthInterceptorConfig : WebMvcConfigurerAdapter() {

    @Autowired
    lateinit var moderatorAuthInterceptor: ModeratorAuthInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(moderatorAuthInterceptor).addPathPatterns("/moderator/**")
    }
}