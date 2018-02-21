package kz.edu.sdu.regsystem.stand.impl.email

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl

//@PropertySource("classpath:application.yml")
@Service
class EmailSender(val env: Environment) {
    @Value("\${spring.mail.host}")
    private val host: String? = null

    @Value("\${spring.mail.port}")
    private val port: Int = 0

    @Value("\${spring.mail.username}")
    private val username: String? = null

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val auth: String? = null

    @Value("\${spring.mail.properties.mail.smtp.starttls.enabled}")
    private val starttlsEnabled: String? = null

    @Value("\${spring.mail.properties.mail.transport.protocol}")
    private val transportProtocol: String? = null

    fun sendMessage(mail: SimpleMailMessage) {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port

        mailSender.username = username
        mailSender.password = env.getProperty("emailPassword")

        val props = mailSender.javaMailProperties
        props.put("mail.transport.protocol", transportProtocol)
        props.put("mail.smtp.auth", auth)
        props.put("mail.smtp.starttls.enable", starttlsEnabled)

        mailSender.send(mail)
    }
}