package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.MedicalInfo
import kz.edu.sdu.regsystem.server.domain.MedicalInfoDocument
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class MedicalInfoRepository(val jdbcTemplate: JdbcTemplate) {
    fun fetchMedicalInfoDocument(userId: Long): MedicalInfoDocument? {
        val query = "SELECT * FROM MedicalInfo AS PI INNER JOIN DOCUMENT AS D ON PI.user_id = D.user_id WHERE PI.user_id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                MedicalInfoDocument(
                    comment = rs.getString("comment"),
                    status = ConclusionStatus.valueOf(rs.getString("status")),
                    userId = userId,
                    form86 = rs.getString("form86"),
                    form63 = rs.getString("form63"),
                    flurography = rs.getString("flurography")
                )
            }, userId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun save(medicalInfo: MedicalInfo) : Long {
        val query = "INSERT INTO MedicalInfo(" +
            "comment, status, user_id) VALUES " +
            "(?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, medicalInfo.comment)
                ps.setString(counter++, medicalInfo.status.name)
                ps.setLong(counter, medicalInfo.userId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun updateStatus(userId: Long, comment: String, status: ConclusionStatus) {
        val query = "UPDATE MedicalInfo SET comment=?, status=? WHERE user_id=?"
        jdbcTemplate.update(query,
            { ps ->
                ps.setString(1, comment)
                ps.setString(2, status.name)
                ps.setLong(3, userId)
            })
    }
}