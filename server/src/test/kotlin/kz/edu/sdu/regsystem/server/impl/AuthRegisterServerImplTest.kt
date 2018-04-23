package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.AuthRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

import org.testng.Assert.*

class AuthRegisterServerImplTest : AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var authRegisterServerImpl: AuthRegisterServerImpl

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun signUp() {
        jdbcTemplate.execute("DELETE FROM USERS")

        val authRequest = AuthRequest(
            email = "dandibobo537@gmail.com",
            password = "google"
        )


    }

    @Test
    fun verifyUser() {
    }

    @Test
    fun resendActivationEmail() {
    }

    @Test
    fun signIn() {
    }

}