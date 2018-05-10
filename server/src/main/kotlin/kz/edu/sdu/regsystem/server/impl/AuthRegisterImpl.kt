package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.controller.model.AuthResponse
import kz.edu.sdu.regsystem.controller.register.AuthRegister
import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.enums.RoleType
import kz.edu.sdu.regsystem.server.domain.enums.UserStatus
import kz.edu.sdu.regsystem.server.exception.*
import kz.edu.sdu.regsystem.server.services.EmailSender
import kz.edu.sdu.regsystem.server.services.JwtService
import kz.edu.sdu.regsystem.server.model.EmailConfig
import kz.edu.sdu.regsystem.server.repositoy.UsersRepository
import kz.edu.sdu.regsystem.server.repositoy.VerificationTokenRepository
import kz.edu.sdu.regsystem.server.utils.Utils
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthRegisterImpl(
    val usersRepository: UsersRepository,
    val emailSender: EmailSender,
    val emailConfig: EmailConfig,
    val verificationTokenRepository: VerificationTokenRepository,
    val jwtService: JwtService) : AuthRegister {

    private val logger = LoggerFactory.getLogger(AuthRegisterImpl::class.java)

    /**
     * throws UserAlreadyExistsException: if user with email to sign up is already in database
     */
    override fun signUp(signUpRequest: AuthRequest) {
        if (usersRepository.ifUserExists(signUpRequest.email)) {
            throw UserAlreadyExistsException("User with email ${signUpRequest.email} already exists")
        }

        val user = User(
            email = signUpRequest.email,
            password = Utils.encrypt(signUpRequest.password),
            regDate = Date(),
            status = UserStatus.NONACTIVE,
            role = RoleType.USER
        )

        val id = usersRepository.save(user)
        user.id = id

        val activationToken = UUID.randomUUID().toString()

        sendEmail(activationToken, signUpRequest.email)

        verificationTokenRepository.save(user.id, activationToken)

    }

    private fun sendEmail(activationToken: String, email: String) {
        logger.info(emailConfig.password)
        logger.info(emailConfig.username)
        val link = "${emailConfig.frontendUrl}/account_verification/email/$activationToken"
        val message = SimpleMailMessage()

        message.setTo(email)
        message.subject = "Регистрация"
        message.text = "Здравствуйте. Для подтверждения регистрации нажите на ссылку ниже\n\n$link"

        logger.info(message.text)
        //todo
        //email text are cached
        emailSender.sendMessage(message)
    }

    override fun verifyUser(token: String): AuthResponse {
//        val verificationTokenDto = verificationTokenRepository.fetchToken(token)
//            ?: throw VerificationTokenDoesNotExistsException("Activation token $token does not exist")
//
//        val userId = verificationTokenDto.user!!.id
//        usersRepository.changeStatus(userId, UserStatus.ACTIVE)
//
//        val user = usersRepository.fetchUserById(userId)
//            ?: throw UserDoesNotExistsException("User with id $userId does not exist")
//
//        return AuthResponse(token = jwtService.generateToken(user))

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resendActivationEmail(email: String) {
//        val user = usersRepository.fetchUserByEmail(email)
//            ?: throw UserDoesNotExistsException("User with email $email does not exist.")
//        val activationToken = verificationTokenRepository.fetchToken(user.id)
//            ?: throw VerificationTokenDoesNotExistsException("Verification token for user $email does not exist")
//        sendEmail(activationToken.token, user.email!!)
    }

    override fun signIn(signInRequest: AuthRequest): AuthResponse {
//        val user = usersRepository.fetchUserByEmail(signInRequest.email)
//            ?: throw UserDoesNotExistsException("User with email ${signInRequest.email} does not exist")
//        if(user.password != Utils.encrypt(signInRequest.password)) {
//            throw PasswordMismatchException("Password for user with email ${signInRequest.email} mismatch")
//        } else if(user.status == UserStatus.NONACTIVE) {
//            throw UserNotConfirmedException("User with email ${signInRequest.email} not confirmed itself")
//        }
//
//        return AuthResponse(token = jwtService.generateToken(user))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}