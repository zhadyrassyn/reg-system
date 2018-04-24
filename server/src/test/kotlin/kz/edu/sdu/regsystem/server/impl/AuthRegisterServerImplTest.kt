package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.server.domain.enums.RoleTypesEnum
import kz.edu.sdu.regsystem.server.repositoy.UsersRepository
import kz.edu.sdu.regsystem.server.repositoy.VerificationTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test
import org.testng.Assert.*
import java.security.MessageDigest
import kotlin.experimental.and

@SpringBootTest
class AuthRegisterServerImplTest : AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var authRegisterServerImpl: AuthRegisterServerImpl

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var verificationTokenRepository: VerificationTokenRepository

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun signUpUserDoesNotExists() {
        jdbcTemplate.execute("DELETE FROM USERS")

        val authRequest = AuthRequest(
            email = "dandibobo537@gmail.com",
            password = "google"
        )

        //
        //
        authRegisterServerImpl.signUp(authRequest)
        //
        //

        val user = usersRepository.fetchUserByEmail(authRequest.email)

        assertNotNull(user)
        assertEquals(user!!.email, authRequest.email)
        
        assertEquals(user.password, convertToMd5(authRequest.password))
        assertEquals(user.role, RoleTypesEnum.USER)

        val verificationToken = verificationTokenRepository.fetchToken(user.id)
        assertNotNull(verificationToken)
        assertNotNull(verificationToken!!.token)

        //assert that email was sent
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

    fun convertToMd5(password: String) : String {
        val md = MessageDigest.getInstance("MD5")
        md.update(password.toByteArray())

        val byteData = md.digest()
        val sb = StringBuffer()
        for (i in 0 until byteData.size) {
            sb.append(Integer.toString((byteData[i] and 0xff.toByte()) + 0x100, 16).substring(1))
        }

        return sb.toString()
    }

}