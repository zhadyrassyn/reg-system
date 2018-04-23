package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.City
import kz.edu.sdu.regsystem.server.domain.School
import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.enums.RoleTypesEnum
import kz.edu.sdu.regsystem.server.domain.enums.UsersStatusEnum
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
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

    fun fetchUserByEmail(email: String) : User? {
        val query = "SELECT * FROM USERS WHERE email=?"

        return jdbcTemplate.queryForObject(
            query, RowMapper { rs, rowNum ->
            User(
                id = rs.getLong("id"),
                email = rs.getString("email"),
                password = rs.getString("password"),
                firstName = rs.getString("first_name"),
                middleName = rs.getString("middle_name"),
                lastName = rs.getString("last_name"),
                birthDate = rs.getDate("bithDate"),
                status = UsersStatusEnum.valueOf(rs.getString("status")),
                city = City(
                    id = rs.getLong("id")
                ),
                school = School(
                    id = rs.getLong("id")
                ),
                role = RoleTypesEnum.valueOf(rs.getString("role"))
            )
        }, email)
    }
}