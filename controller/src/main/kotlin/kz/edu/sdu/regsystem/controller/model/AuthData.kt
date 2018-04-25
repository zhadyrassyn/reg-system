package kz.edu.sdu.regsystem.controller.model

data class AuthRequest(var email: String, var password: String)

data class AuthResponse(val token: String)

