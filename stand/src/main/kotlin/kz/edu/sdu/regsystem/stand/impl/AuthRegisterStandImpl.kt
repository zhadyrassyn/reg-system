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
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
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
        db.users[id]?.userStatus = UserStatus.ACTIVE

        val user = db.users[id]
        val token = generateJwtToken(user!!)

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

        db.users[newUser.id] = newUser
        db.userRoles[newUser.id] = RoleType.USER

        //sending message
        val token = UUID.randomUUID().toString()
        sendEmail(token, signUpRequest.email)

        db.verificationTokens[newUser.id] = token
    }

    override fun signIn(signInRequest: AuthRequest): AuthResponse {
        //checking for user existence
        val user = db.users.values.firstOrNull {
            it.email == signInRequest.email && it.password == signInRequest.password
        } ?: throw UserDoesNotExistsException("Email or password is wrong")

        if(user.userStatus == UserStatus.NONACTIVE) {
            throw UserNotActiveException("User with email ${signInRequest.email} not activated")
        }

        val token = generateJwtToken(user)

        return AuthResponse(token)
    }

    private fun generateJwtToken(user: User): String {
        val roleType = db.userRoles[user.id] ?: throw Exception("Role is undefined")
        val scope : String =
            if(roleType == RoleType.USER) RoleType.USER.toString()
            else RoleType.MODERATOR.toString()

        val key = env.getProperty("jwtKey") ?: throw RuntimeException("Jwt key does not exists in environment variables!")
        return Jwts.builder()
            .setSubject(user.email)
            .signWith(SignatureAlgorithm.HS512, key)
            .setExpiration(getNextDay())
            .setIssuedAt(Date())
            .claim("scope", scope)
            .compact()
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
        message.text = "Здравствуйте. Для подтверждения регистрации нажите на ссылку ниже\n\n$link"

        println(message.text)
        //todo
        //email text are cached
        emailSender.sendMessage(message)
    }
}