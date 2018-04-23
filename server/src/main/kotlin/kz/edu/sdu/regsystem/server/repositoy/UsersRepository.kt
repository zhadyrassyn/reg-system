package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class UsersRepository(val jdbcTemplate: JdbcTemplate) {
    fun ifUserExists(email: String) : Boolean {
        val query = "SELECT COUNT(*) AS COUNT FROM USERS WHERE email=?"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { con ->
                val ps = con.prepareStatement(query, arrayOf("count"))
                ps.setString(1, email)
                ps
            },
            keyHolder
        )

        return keyHolder.key!!.toInt() == 0
    }
}