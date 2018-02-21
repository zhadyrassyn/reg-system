package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.AuthResponse
import kz.edu.sdu.regsystem.controller.model.AuthRequest

interface AuthRegister {
    fun signIn(signInRequest: AuthRequest): AuthResponse

    fun signUp(signUpRequest: AuthRequest)
}