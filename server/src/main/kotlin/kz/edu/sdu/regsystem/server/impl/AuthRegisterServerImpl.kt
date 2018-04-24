package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.controller.model.AuthResponse
import kz.edu.sdu.regsystem.controller.register.AuthRegister
import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.enums.RoleTypesEnum
import kz.edu.sdu.regsystem.server.domain.enums.UsersStatusEnum
import kz.edu.sdu.regsystem.server.exception.UserAlreadyExistsException
import kz.edu.sdu.regsystem.server.repositoy.UsersRepository
import kz.edu.sdu.regsystem.server.utils.Utils
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthRegisterServerImpl(val usersRepository: UsersRepository) : AuthRegister{

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
            status = UsersStatusEnum.NONACTIVE,
            role = RoleTypesEnum.USER
        )

        val id = usersRepository.save(user)
        user.id = id

        val token = UUID.randomUUID().toString()


    }

    override fun verifyUser(token: String): AuthResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resendActivationEmail(email: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signIn(signInRequest: AuthRequest): AuthResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}