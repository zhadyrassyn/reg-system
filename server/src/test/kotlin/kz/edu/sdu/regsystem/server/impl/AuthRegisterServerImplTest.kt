package kz.edu.sdu.regsystem.server.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.enums.RoleTypesEnum
import kz.edu.sdu.regsystem.server.domain.enums.UsersStatusEnum
import kz.edu.sdu.regsystem.server.exception.UserAlreadyExistsException
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

    @Autowired
    lateinit var env: Environment

    val authRequest = AuthRequest(
        email = "bojaxim@uemail99.com",
        password = "google"
    )

    var user: User? = null

    fun initDb() {
        jdbcTemplate.execute("DELETE FROM VERIFICATION_TOKEN")
        jdbcTemplate.execute("DELETE FROM USERS")

        user = User(
            email = authRequest.email,
            password = authRequest.password,
            status = UsersStatusEnum.NONACTIVE,
            role = RoleTypesEnum.USER
        )
    }

    @Test
    fun signUpUserDoesNotExists() {
        initDb()

        //
        //
        authRegisterServerImpl.signUp(authRequest)
        //
        //

        val user = usersRepository.fetchUserByEmail(authRequest.email)

        assertNotNull(user)
        assertEquals(user!!.email, authRequest.email)
        
        assertEquals(user.password, Utils.encrypt(authRequest.password))
        assertEquals(user.role, RoleTypesEnum.USER)

        val verificationToken = verificationTokenRepository.fetchToken(user.id!!)
        assertNotNull(verificationToken)
        assertNotNull(verificationToken!!.token)

        //assert that email was sent
    }

    @Test(expectedExceptions = [(UserAlreadyExistsException::class)])
    fun signUpUserAlreadyExists() {
        initDb()

        usersRepository.save(user!!)

        //
        //
        authRegisterServerImpl.signUp(authRequest)
        //
        //
    }

    @Test
    fun verifyUser() {
        initDb()

        val id = usersRepository.save(user!!)
        val activationToken = "activationToken"
        verificationTokenRepository.save(id, "activationToken")

        //
        //
        val response = authRegisterServerImpl.verifyUser(activationToken)
        //
        //

        assertNotNull(response)
        assertNotNull(response.token)
        val jwtKey = env.getProperty("jwt.key")

        val jwtBody = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(response.token).body
        val email = jwtBody.subject
        val idValue = jwtBody.get("id")
        val scope = jwtBody.get("scope")

        assertNotNull(email)
        assertEquals(email, authRequest.email)
        assertNotNull(idValue)
        assertEquals(idValue.toString().toLong(), id)
        assertNotNull(scope)
        assertEquals(scope.toString().trim(), user!!.role.name)

        val user = usersRepository.fetchUserById(id)
        assertNotNull(user)
        assertEquals(user!!.status, UsersStatusEnum.ACTIVE)
    }

    @Test
    fun signIn() {
    }

}