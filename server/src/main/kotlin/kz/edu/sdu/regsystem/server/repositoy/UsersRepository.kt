package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.enums.RoleType
import kz.edu.sdu.regsystem.server.domain.enums.UserStatus
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*

@Repository
class UsersRepository(val jdbcTemplate: JdbcTemplate) {
    fun ifUserExists(email: String) : Boolean {
        val query = "SELECT COUNT(*) AS TOTAL FROM USERS WHERE email=?"

        val amount = jdbcTemplate.queryForObject(query, RowMapper { rs, _ ->
            rs.getString("total")
        }, email) ?: throw SQLException("Cannot execute statement $query")

        return amount.toInt() != 0
    }

    fun fetchUserByEmail(email: String) : User? {
        val query = "SELECT * FROM USERS WHERE email=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                User(
                    id = rs.getLong("id"),
                    email = rs.getString("email"),
                    password = rs.getString("password"),
                    regDate = rs.getDate("reg_date"),
                    status = UserStatus.valueOf(rs.getString("status")),
                    role = RoleType.valueOf(rs.getString("role"))
                )
            }, email)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun save(user: User): Long {
        val query = "INSERT INTO USERS(email, password, status, role, reg_date) VALUES (?, ?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, user.email)
                ps.setString(counter++, user.password)
                ps.setString(counter++, user.status.name)
                ps.setString(counter++, user.role.name)
                ps.setTimestamp(counter, Timestamp(user.regDate.time))
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun changeStatus(id: Long, status: UserStatus) {
        val query = "UPDATE USERS SET status=? WHERE id=?"
        jdbcTemplate.update(query,
            {ps ->
                ps.setString(1, status.name)
                ps.setLong(2, id)
            })
    }

//    fun fetchUserById(id: Long): User? {
//        val query = "SELECT * FROM USERS WHERE id=?"
//
//        return jdbcTemplate.queryForObject(
//            query, RowMapper { rs, rowNum ->
//            User(
//                id = rs.getLong("id"),
//                email = rs.getString("email"),
//                password = rs.getString("password"),
//                firstName = rs.getString("first_name"),
//                middleName = rs.getString("middle_name"),
//                lastName = rs.getString("last_name"),
//                birthDate = rs.getDate("bithDate"),
//                status = UserStatus.valueOf(rs.getString("status")),
//                city = City(
//                    id = rs.getLong("id")
//                ),
//                school = School(
//                    id = rs.getLong("id")
//                ),
//                role = RoleType.valueOf(rs.getString("role"))
//            )
//        }, id)
//    }
}