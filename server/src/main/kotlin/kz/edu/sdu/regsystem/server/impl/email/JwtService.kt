package kz.edu.sdu.regsystem.server.impl.email

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.model.JwtConfig
import kz.edu.sdu.regsystem.server.utils.Utils
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(val jwtConfig: JwtConfig) {
    fun generateToken(user: User): String {
        val key = jwtConfig.key

        return Jwts.builder()
            .setSubject(user.email)
            .signWith(SignatureAlgorithm.HS512, key)
            .setExpiration(Utils.getNextDay())
            .setIssuedAt(Date())
            .claim("scope", user.role)
            .claim("id", user.id.toString())
            .compact()
    }
}