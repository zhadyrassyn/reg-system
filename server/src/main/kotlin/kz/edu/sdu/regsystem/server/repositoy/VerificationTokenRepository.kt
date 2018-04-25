package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.VerificationToken
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*

@Repository
class VerificationTokenRepository(val jdbcTemplate: JdbcTemplate) {
    fun fetchToken(userId: Long) : VerificationToken? {
        val query = "SELECT * FROM VERIFICATION_TOKEN WHERE user_id=?"

        return jdbcTemplate.queryForObject(query,
            RowMapper { rs, rowNum ->
                VerificationToken(
                id = rs.getLong("id"),
                token = rs.getString("token"),
                createdDate = rs.getDate("created_date"),
                user = User(
                    id = rs.getLong("user_id")
                )
            )
        },
            userId
        )
    }

    fun save(userId: Long, activationToken: String) : Long {
        val query = "INSERT INTO VERIFICATION_TOKEN(token, created_date, user_id) VALUES (?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, activationToken)
                ps.setTimestamp(counter++, Timestamp(Date().time))
                ps.setLong(counter, userId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }
}