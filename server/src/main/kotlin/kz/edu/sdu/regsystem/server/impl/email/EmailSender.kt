package kz.edu.sdu.regsystem.server.impl.email

import kz.edu.sdu.regsystem.server.model.EmailConfig
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service

@Service
class EmailSender(val emailConfig: EmailConfig) {

    fun sendMessage(mail: SimpleMailMessage) {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = emailConfig.host
        mailSender.port = emailConfig.port

        mailSender.username = emailConfig.username
        mailSender.password = emailConfig.password

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = emailConfig.transportProtocol
        props["mail.smtp.auth"] = emailConfig.auth
        props["mail.smtp.starttls.enable"] = emailConfig.starttlsEnabled

        mailSender.send(mail)
    }


}