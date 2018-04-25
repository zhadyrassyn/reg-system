package kz.edu.sdu.regsystem.server.config

import kz.edu.sdu.regsystem.server.exception.BootstrapException
import kz.edu.sdu.regsystem.server.model.EmailConfig
import kz.edu.sdu.regsystem.server.model.JwtConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class BeansConfig(val env: Environment) {

    val EMAIL_HOST = "spring.mail.host"
    val EMAIL_PORT = "spring.mail.port"
    val EMAIL_USERNAME = "spring.mail.username"
    val EMAIL_PASSWORD = "emailPassword"
    val EMAIL_TRANSPORT_PROTOCOL = "spring.mail.properties.mail.transport.protocol"
    val EMAIL_AUTH = "spring.mail.properties.mail.smtp.auth"
    val EMAIL_STARTTLS_ENABLED = "spring.mail.properties.mail.smtp.starttls.enabled"

    val EMAIL_FRONTEND_URL="frontend.url"

    val JWT_KEY="jwt.key"

    @Bean("emailConfig")
    fun getEmailConfig() : EmailConfig {
        return EmailConfig(
            host = env.getProperty(EMAIL_HOST) ?: throw BootstrapException(
                "Cannot find '$EMAIL_HOST' config"),
            port = env.getProperty(EMAIL_PORT, Int::class.java) ?: throw BootstrapException(
                "Cannot find '$EMAIL_PORT' config"),
            username = env.getProperty(EMAIL_USERNAME) ?: throw BootstrapException(
                "Cannot find '$EMAIL_USERNAME' config"
            ),
            password = env.getProperty(EMAIL_PASSWORD) ?: throw BootstrapException(
                "Cannot find '$EMAIL_PASSWORD' config"
            ),
            transportProtocol = env.getProperty(EMAIL_TRANSPORT_PROTOCOL) ?: throw BootstrapException(
                "Cannot find '$EMAIL_TRANSPORT_PROTOCOL' config"
            ),
            auth = env.getProperty(EMAIL_AUTH, Boolean::class.java) ?: throw BootstrapException(
                "Cannot find '$EMAIL_AUTH' config"
            ),
            starttlsEnabled = env.getProperty(EMAIL_STARTTLS_ENABLED, Boolean::class.java) ?:
                throw BootstrapException("Cannot find '$EMAIL_STARTTLS_ENABLED' config"),
            frontendUrl = env.getProperty(EMAIL_FRONTEND_URL) ?: throw BootstrapException(
                "Cannot find '$EMAIL_FRONTEND_URL' config"
            )
        )
    }

    @Bean("jwtConfig")
    fun getJwtConfig() : JwtConfig {
        return JwtConfig(
            key = env.getProperty(JWT_KEY) ?: throw BootstrapException(
                "Cannot find '$JWT_KEY' config"
            )
        )
    }
}