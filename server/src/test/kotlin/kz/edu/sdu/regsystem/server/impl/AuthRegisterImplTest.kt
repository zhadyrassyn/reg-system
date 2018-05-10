package kz.edu.sdu.regsystem.server.impl

import io.jsonwebtoken.Jwts
import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.enums.RoleType
import kz.edu.sdu.regsystem.server.domain.enums.UserStatus
import kz.edu.sdu.regsystem.server.exception.PasswordMismatchException
import kz.edu.sdu.regsystem.server.exception.UserAlreadyExistsException
import kz.edu.sdu.regsystem.server.exception.UserDoesNotExistsException
import kz.edu.sdu.regsystem.server.exception.UserNotConfirmedException
import kz.edu.sdu.regsystem.server.repositoy.UsersRepository
import kz.edu.sdu.regsystem.server.repositoy.VerificationTokenRepository
import kz.edu.sdu.regsystem.server.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.annotations.Test
import java.util.*

@SpringBootTest
class AuthRegisterImplTest : AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var authRegisterImpl: AuthRegisterImpl

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var verificationTokenRepository: VerificationTokenRepository

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var env: Environment

    var authRequest = AuthRequest(
        email = "xucoh@ethersportz.info",
        password = Utils.encrypt("google")
    )

    lateinit var user: User

    fun initDb() {
        jdbcTemplate.execute("DELETE FROM VERIFICATION_TOKEN")
        jdbcTemplate.execute("DELETE FROM USERS")

        user = User(
            email = authRequest.email,
            password = authRequest.password,
            regDate = Date(),
            status = UserStatus.NONACTIVE,
            role = RoleType.USER
        )
    }

    @Test
    fun signUp() {
        initDb()

        //
        //
        authRegisterImpl.signUp(authRequest)
        //
        //

        val user = usersRepository.fetchUserByEmail(authRequest.email)

        assertNotNull(user)
        assertEquals(user!!.email, authRequest.email)

        assertEquals(user.password, Utils.encrypt(authRequest.password))
        assertEquals(user.role, RoleType.USER)
        assertEquals(user.status, UserStatus.NONACTIVE)

        val verificationToken = verificationTokenRepository.fetchToken(user.id)
        assertNotNull(verificationToken)
        assertNotNull(verificationToken!!.token)

        //assert that email was sent
    }

    @Test(expectedExceptions = [(UserAlreadyExistsException::class)])
    fun signUpUserAlreadyExists() {
        initDb()

        usersRepository.save(user)

        //
        //
        authRegisterImpl.signUp(authRequest)
        //
        //
    }

    @Test
    fun verifyUser() {
        initDb()

        val id = usersRepository.save(user)
        val activationToken = "activationToken"
        verificationTokenRepository.save(id, "activationToken")

        //
        //
        val response = authRegisterImpl.verifyUser(activationToken)
        //
        //

        assertNotNull(response)
        assertNotNull(response.token)

        val jwtKey = env.getProperty("jwtKey")
        val jwtBody = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(response.token).body
        val email = jwtBody.subject
        val idValue = jwtBody["id"]
        val scope = jwtBody["scope"]

        assertNotNull(email)
        assertEquals(email, authRequest.email)
        assertNotNull(idValue)
        assertEquals(idValue.toString().toLong(), id)
        assertNotNull(scope)
        assertEquals(scope.toString().trim(), user.role.name)

        val user = usersRepository.fetchUserById(id)
        assertNotNull(user)
        assertEquals(user!!.status, UserStatus.ACTIVE)
    }

    @Test
    fun signInSuccess() {
        initDb()
        user.status = UserStatus.ACTIVE
        val id = usersRepository.save(user)
        user.id = id

        authRequest.password = "google"
        //
        //
        val response = authRegisterImpl.signIn(authRequest)
        //
        //

        assertNotNull(response)
        assertNotNull(response.token)

        val jwtKey = env.getProperty("jwtKey")

        val jwtBody = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(response.token).body
        val email = jwtBody.subject
        val idValue = jwtBody["id"]
        val scope = jwtBody["scope"]

        assertNotNull(email)
        assertEquals(email, authRequest.email)
        assertNotNull(idValue)
        assertEquals(idValue.toString().toLong(), id)
        assertNotNull(scope)
        assertEquals(scope.toString().trim(), user.role.name)
    }

    @Test(expectedExceptions = [UserDoesNotExistsException::class])
    fun signInFailUserDoesNotExist() {
        initDb()

        //
        //
        val response = authRegisterImpl.signIn(authRequest)
        //
        //
    }

    @Test(expectedExceptions = [PasswordMismatchException::class])
    fun signInPasswordMismatch() {
        initDb()
        usersRepository.save(user)

        authRequest.password = "123"
        //
        //
        val response = authRegisterImpl.signIn(authRequest)
        //
        //
    }

    @Test(expectedExceptions = [UserNotConfirmedException::class])
    fun signInUserNotConfirmed() {
        initDb()
        usersRepository.save(user)

        authRequest.password = "google"
        //
        //
        val response = authRegisterImpl.signIn(authRequest)
        //
        //
    }

}