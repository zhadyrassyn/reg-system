package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.UserRow
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import kz.edu.sdu.regsystem.server.domain.enums.GenderType
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

    fun fetchUserById(id: Long): User? {
        val query = "SELECT * FROM USERS WHERE id=?"

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
        }, id)
    }

    fun fetchTotal(text: String): Int {
        val query = "SELECT COUNT(*) AS total FROM USERS AS U" +
            "  INNER JOIN PersonalInfo AS PI ON PI.user_id = U.id" +
            "  INNER JOIN EducationInfo AS EI ON EI.user_id = U.id" +
            "  INNER JOIN MedicalInfo AS MI ON MI.user_id = U.id WHERE U.status='ACTIVE'" +
            "  AND (LOWER(PI.first_name) LIKE '%$text%'" +
            "  OR LOWER(PI.middle_name) LIKE '%$text%'" +
            "  OR LOWER(PI.last_name) LIKE '%$text%'" +
            "  OR LOWER(PI.iin) LIKE '%$text%'" +
            "  OR LOWER(U.email) LIKE '%$text%'" +
            "  OR LOWER(PI.gender) LIKE '%$text%')"

        val amount = jdbcTemplate.queryForObject(query, RowMapper { rs, _ ->
            rs.getString("total")
        }) ?: throw SQLException("Cannot execute statement $query")

        return amount.toInt()
    }

    fun fetchUsers(text: String, offset: Int, perPage: Int) : List<UserRow> {
        val query = "" +
            "SELECT U.id, PI.first_name, PI.middle_name, PI.last_name, PI.iin, U.email, PI.gender," +
            "PI.status AS pi_status, EI.status AS ei_status, MI.status AS mi_status FROM USERS AS U" +
            "  INNER JOIN PersonalInfo AS PI ON PI.user_id = U.id" +
            "  INNER JOIN EducationInfo AS EI ON EI.user_id = U.id" +
            "  INNER JOIN MedicalInfo AS MI ON MI.user_id = U.id" +
            "  WHERE U.status='ACTIVE'" +
            "  AND LOWER(PI.first_name) LIKE '%$text%'" +
            "  AND (LOWER(PI.middle_name) LIKE '%$text%'" +
            "  OR LOWER(PI.last_name) LIKE '%$text%'" +
            "  OR LOWER(PI.iin) LIKE '%$text%'" +
            "  OR LOWER(U.email) LIKE '%$text%'" +
            "  OR LOWER(PI.gender) LIKE '%$text%')" +
            "  LIMIT $perPage OFFSET $offset"

        return jdbcTemplate.query(query, { rs, _ ->
            UserRow(
                id = rs.getLong("id"),
                firstName = rs.getString("first_name"),
                middleName = rs.getString("middle_name"),
                lastName = rs.getString("last_name"),
                iin = rs.getString("iin"),
                email = rs.getString("email"),
                gender = GenderType.valueOf(rs.getString("gender")),
                pi_status = ConclusionStatus.valueOf(rs.getString("pi_status")),
                ei_status = ConclusionStatus.valueOf(rs.getString("ei_status")),
                mi_status = ConclusionStatus.valueOf(rs.getString("mi_status"))
            )
        })
    }
}