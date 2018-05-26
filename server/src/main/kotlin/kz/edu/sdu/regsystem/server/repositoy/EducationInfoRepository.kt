package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.controller.model.SaveEducationInfoRequestData
import kz.edu.sdu.regsystem.server.domain.EducationInfo
import kz.edu.sdu.regsystem.server.domain.EducationInfoDocument
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class EducationInfoRepository(
    val jdbcTemplate: JdbcTemplate
) {
    fun get(userId: Long): EducationInfo? {
        val query = "SELECT * FROM EducationInfo WHERE user_id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                EducationInfo(
                    id = rs.getLong("id"),
                    areaId = rs.getLong("area_id"),
                    cityId = rs.getLong("city_id"),
                    schoolId = rs.getLong("school_id"),
                    schoolFinish = rs.getDate("school_finish"),
                    entAmount = rs.getInt("ent_amount"),
                    entCertificateNumber = rs.getString("ent_certificate_number"),
                    ikt = rs.getString("ikt"),
                    facultyId = rs.getLong("faculty_id"),
                    specialtyId = rs.getLong("specialty_id"),
                    comment = rs.getString("comment"),
                    status = ConclusionStatus.valueOf(rs.getString("status")),
                    userId = rs.getLong("user_id")
                )
            }, userId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun fetchEducationInfoDocument(userId: Long) : EducationInfoDocument? {
        val query = "SELECT * FROM EducationInfo AS PI INNER JOIN DOCUMENT AS D ON PI.user_id = D.user_id WHERE PI.user_id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                EducationInfoDocument(
                    id = rs.getLong("id"),
                    areaId = rs.getLong("area_id"),
                    cityId = rs.getLong("city_id"),
                    schoolId = rs.getLong("school_id"),
                    schoolFinish = rs.getDate("school_finish"),
                    entAmount = rs.getInt("ent_amount"),
                    entCertificateNumber = rs.getString("ent_certificate_number"),
                    ikt = rs.getString("ikt"),
                    facultyId = rs.getLong("faculty_id"),
                    specialtyId = rs.getLong("specialty_id"),
                    comment = rs.getString("comment"),
                    status = ConclusionStatus.valueOf(rs.getString("status")),
                    userId = userId,
                    school_diploma = rs.getString("school_diploma"),
                    ent_certificate = rs.getString("ent_certificate")
                )
            }, userId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun save(educationInfo: EducationInfo): Long {
        val query = "INSERT INTO EducationInfo(area_id, city_id, school_id, " +
            "school_finish, ent_amount, ent_certificate_number, ikt, " +
            "faculty_id, specialty_id, " +
            "comment, status, user_id) VALUES" +
            "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setLong(counter++, educationInfo.areaId)
                ps.setLong(counter++, educationInfo.cityId)
                ps.setLong(counter++, educationInfo.schoolId)
                ps.setTimestamp(counter++, Timestamp(educationInfo.schoolFinish.time))
                ps.setInt(counter++, educationInfo.entAmount)
                ps.setString(counter++, educationInfo.entCertificateNumber)
                ps.setString(counter++, educationInfo.ikt)
                ps.setLong(counter++, educationInfo.facultyId)
                ps.setLong(counter++, educationInfo.specialtyId)
                ps.setString(counter++, educationInfo.comment)
                ps.setString(counter++, educationInfo.status.name)
                ps.setLong(counter, educationInfo.userId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun update(educationInfo: EducationInfo) {
        val query = "UPDATE EducationInfo SET area_id=?, city_id=?, school_id=?, " +
            "school_finish=?, ent_amount=?, ent_certificate_number=?, ikt=?, " +
            "faculty_id=?, specialty_id=?, comment=?, status=?, user_id=?" +
            "WHERE id=?"

        jdbcTemplate.update(query,
            { ps ->
                var counter = 1
                ps.setLong(counter++, educationInfo.areaId)
                ps.setLong(counter++, educationInfo.cityId)
                ps.setLong(counter++, educationInfo.schoolId)
                ps.setTimestamp(counter++, Timestamp(educationInfo.schoolFinish.time))
                ps.setInt(counter++, educationInfo.entAmount)
                ps.setString(counter++, educationInfo.entCertificateNumber)
                ps.setString(counter++, educationInfo.ikt)
                ps.setLong(counter++, educationInfo.facultyId)
                ps.setLong(counter++, educationInfo.specialtyId)
                ps.setString(counter++, educationInfo.comment)
                ps.setString(counter++, educationInfo.status.name)
                ps.setLong(counter++, educationInfo.userId)
                ps.setLong(counter, educationInfo.id)
            })
        
    }
}