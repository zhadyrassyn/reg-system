package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.controller.model.AuthResponse
import kz.edu.sdu.regsystem.controller.register.AuthRegister
import kz.edu.sdu.regsystem.server.exception.UserAlreadyExistsException
import kz.edu.sdu.regsystem.server.repositoy.UsersRepository
import org.springframework.stereotype.Service

@Service
class AuthRegisterServerImpl(val usersRepository: UsersRepository) : AuthRegister{

    @Throws(UserAlreadyExistsException::class)
    override fun signUp(signUpRequest: AuthRequest) {
        if (usersRepository.ifUserExists(signUpRequest.email)) {
            throw UserAlreadyExistsException("User with email ${signUpRequest.email} already exists")
        }

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