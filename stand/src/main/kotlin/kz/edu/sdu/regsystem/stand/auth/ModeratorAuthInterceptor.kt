package kz.edu.sdu.regsystem.stand.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
import kz.edu.sdu.regsystem.stand.model.exceptions.ForbiddenException
import kz.edu.sdu.regsystem.stand.model.exceptions.UnauthorizedException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ModeratorAuthInterceptor(
    val db: Db,
    val env: Environment
) : HandlerInterceptorAdapter() {

    private val AUTH_HEADER = "Authorization"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authHeader = request.getHeader(AUTH_HEADER)

        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
            throw UnauthorizedException("Unauthorized")
        }

        val token = authHeader.substring(7)
        val jwtKey = env.getProperty("jwtKey")


        val email = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).body.subject ?:
            throw SignatureException("Cannot parse jwt")

        val user = db.users.values.firstOrNull { it.email == email }
            ?: throw UserDoesNotExistsException("User with email $email does not exists")

        if (db.userRoles[user.id] != RoleType.MODERATOR) {
            throw ForbiddenException("Rest end point is forbidden for user $email")
        }

        return true
    }

}