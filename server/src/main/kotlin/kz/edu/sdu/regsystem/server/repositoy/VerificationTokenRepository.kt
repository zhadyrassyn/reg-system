package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.VerificationToken
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

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
}