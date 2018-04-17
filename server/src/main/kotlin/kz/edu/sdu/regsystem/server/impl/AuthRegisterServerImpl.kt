package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.controller.model.AuthResponse
import kz.edu.sdu.regsystem.controller.register.AuthRegister
import org.springframework.stereotype.Service

@Service
class AuthRegisterServerImpl : AuthRegister{
    override fun signUp(signUpRequest: AuthRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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