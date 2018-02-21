package kz.edu.sdu.regsystem.controller.model

data class AuthRequest(val email: String, val password: String)

data class AuthResponse(val token: String)

