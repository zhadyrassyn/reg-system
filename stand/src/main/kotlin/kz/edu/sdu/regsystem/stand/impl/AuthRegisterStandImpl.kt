package kz.edu.sdu.regsystem.stand.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kz.edu.sdu.regsystem.controller.model.AuthResponse
import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.controller.register.AuthRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.User
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.UserAlreadyExistsException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserNotActiveException
import org.springframework.stereotype.Component
import kz.edu.sdu.regsystem.stand.impl.email.EmailSender
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import java.util.*


@Component
class AuthRegisterStandImpl(
    val db: Db,
    val emailSender: EmailSender,
    val env: Environment) : AuthRegister {

    override fun verifyUser(token: String): AuthResponse {
        val id = db.verificationTokens.entries.firstOrNull { it -> it.value == token }?.key ?: throw BadRequestException("bad account verification url")
        db.users.get(id)?.userStatus = UserStatus.ACTIVE

        val token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRYW1IYkdKNzg1c0hVN0V1SXpJbl9qVlMyb09qTW1haGFZLWd6elZwUWlVIn0.eyJqdGkiOiI1NWFhMTk0MS00ZGFmLTRlOGYtYjcyNi00MzQ1MzMzZmRjNDkiLCJleHAiOjE1MTkxMTM5NzEsIm5iZiI6MCwiaWF0IjoxNTE5MTEzNjcxLCJpc3MiOiJodHRwOi8vcHJlLmFjY291bnRzLmtjZWxsLmt6L2F1dGgvcmVhbG1zL0IyQiIsImF1ZCI6ImFkbWluLWNsaSIsInN1YiI6ImY6OGEyNGY0YjAtYTNkNi00ZGI4LTllOGMtMGJhMjc3N2RiNGYxOjdmYzNkZTU0LWNlODktNDFkMi05MjVlLTliZGU3OGVlZTM1MiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFkbWluLWNsaSIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjE4NTVhMGViLTUxNjMtNDQ4Mi05OGM1LTNiYTBkMzQyN2IyNiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOltdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJuYW1lIjoibWFkaXlhciBub2dheWV2IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiNzcwMTIxMTQyMzIiLCJnaXZlbl9uYW1lIjoibWFkaXlhciIsImZhbWlseV9uYW1lIjoibm9nYXlldiIsImVtYWlsIjoibWFkaXlhci5ub2dheWV2QGtjZWxsLmNvbSAgICAgICAgICAgICAgICJ9.Rv5M1mf58J3zkYURLgun6Q_9UIX4HMoH12GbohMT7sk0H1LfflL4QcdlXIxQko_bNlr8mmTWG8cFl1u_3hNhhanN_bqzDf1R3D19ToaCvhvpLxVua--c08ybfbMd2LCNE7Zt_H51a0XhCkZLQ0SJYLyVryVzMHnYmP8OG-zXeXhgr53R0lHZd8WW3oe2uUSZPn9SKuJjDPnkoi1AcuV_62MHcc6oDWZhy7BbIUnGEyeiuYGx5Pj141O_RPiq482bJaWQbqXaSoU55irUPNeWqpcfqKc5QFUfyMMt-hWQ2I9q3IGdr4brMdYfEwJx3bI_jip-jWwP3Fhs56ulQhu_hQ"

        return AuthResponse(token)
    }

    override fun signUp(signUpRequest: AuthRequest) {
        val user = db.users.values.firstOrNull {
            it.email == signUpRequest.email && it.password == signUpRequest.password
        }
        if(user != null) {
            throw UserAlreadyExistsException("User with this email already exists")
        }
        //save
        val newUser = User(
                id = db.longCounter.incrementAndGet(),
                email = signUpRequest.email,
                password = signUpRequest.password,
                userStatus = UserStatus.NONACTIVE
        )

        db.users.put(newUser.id, newUser)

        //sending message
        val token = UUID.randomUUID().toString()
        sendEmail(token, signUpRequest.email)

        db.verificationTokens.put(newUser.id, token)
    }

    override fun signIn(signInRequest: AuthRequest): AuthResponse {
        //checking for user existence
        val user = db.users.values.firstOrNull {
            it.email == signInRequest.email && it.password == signInRequest.password
        } ?: throw UserDoesNotExistsException("Email or password is wrong")

        if(user.userStatus == UserStatus.NONACTIVE) {
            throw UserNotActiveException("User with email ${signInRequest.email} not activated")
        }

        val key = env.getProperty("jwtKey") ?: throw RuntimeException("Jwt key does not exists in environment variables!")
        val token = Jwts.builder()
            .setSubject(user.email)
            .signWith(SignatureAlgorithm.HS512, key)
            .setExpiration(getNextDay())
            .compact()

        return AuthResponse(token)
    }

    private fun getNextDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        return calendar.time
    }

    override fun resendActivationEmail(email: String) {
        val user = db.users.values.firstOrNull { it.email == email }
            ?: throw UserDoesNotExistsException("User with email $email not found!")

        val activationToken = db.verificationTokens[user.id]
            ?: throw Exception("Activation token not been assigned to $email")

        sendEmail(activationToken, email)
    }

    private fun sendEmail(activationToken: String, email: String) {
        val link = "http://localhost:8080/account_verification/email/$activationToken"
        val message = SimpleMailMessage()

        message.setTo(email)
        message.subject = "Регистрация"
        message.text = "Здравствуйте. Для подтверждения регистрации нажите на ссылку ниже\n\n" + link

        emailSender.sendMessage(message)
    }
}