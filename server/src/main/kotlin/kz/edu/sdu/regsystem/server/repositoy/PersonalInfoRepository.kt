package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.controller.model.SavePersonalInfoRequest
import kz.edu.sdu.regsystem.server.domain.PersonalInfo
import kz.edu.sdu.regsystem.server.domain.PersonalInfoDocument
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class PersonalInfoRepository(val jdbcTemplate: JdbcTemplate) {
    fun fetchPersonalInfo(userId: Long): PersonalInfo? {
        val query = "SELECT * FROM PersonalInfo WHERE user_id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                PersonalInfo(
                    id = rs.getLong("id"),
                    firstName = rs.getString("first_name"),

                    middleName = rs.getString("middle_name"),
                    lastName = rs.getString("last_name"),
                    gender = rs.getString("gender"),
                    birthDate = rs.getDate("birth_date"),
                    givenDate = rs.getDate("given_date"),
                    givenPlace = rs.getString("given_place"),
                    iin = rs.getString("iin"),
                    ud_number = rs.getString("ud_number"),
                    nationality = rs.getString("nationality"),
                    blood_group = rs.getString("blood_group"),
                    citizenship = rs.getString("citizenship"),

                    birthPlaceId = rs.getLong("birth_place_id"),

                    mobilePhone = rs.getString("mobile_phone"),
                    telPhone = rs.getString("tel_phone"),

                    factFlat = rs.getString("fact_flat"),
                    factFraction = rs.getString("fact_fraction"),
                    factHouse = rs.getString("fact_house"),
                    factStreet = rs.getString("fact_street"),

                    regFlat = rs.getString("reg_flat"),
                    regFraction = rs.getString("reg_fraction"),
                    regHouse = rs.getString("reg_house"),
                    regStreet = rs.getString("reg_street"),

                    comment = rs.getString("comment"),
                    status = ConclusionStatus.valueOf(rs.getString("status")),
                    userId = rs.getLong("user_id")
                )
            }, userId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun fetchPersonalInfoDocument(userId: Long): PersonalInfoDocument? {
        val query = "SELECT * FROM PersonalInfo AS PI INNER JOIN DOCUMENT AS D ON PI.user_id = D.user_id WHERE PI.user_id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                PersonalInfoDocument(
                    firstName = rs.getString("first_name"),
                    middleName = rs.getString("middle_name"),
                    lastName = rs.getString("last_name"),
                    gender = rs.getString("gender"),
                    birthDate = rs.getDate("birth_date"),
                    givenDate = rs.getDate("given_date"),
                    givenPlace = rs.getString("given_place"),
                    iin = rs.getString("iin"),
                    ud_number = rs.getString("ud_number"),
                    nationality = rs.getString("nationality"),
                    blood_group = rs.getString("blood_group"),
                    citizenship = rs.getString("citizenship"),

                    birthPlaceId = rs.getLong("birth_place_id"),

                    mobilePhone = rs.getString("mobile_phone"),
                    telPhone = rs.getString("tel_phone"),

                    factFlat = rs.getString("fact_flat"),
                    factFraction = rs.getString("fact_fraction"),
                    factHouse = rs.getString("fact_house"),
                    factStreet = rs.getString("fact_street"),

                    regFlat = rs.getString("reg_flat"),
                    regFraction = rs.getString("reg_fraction"),
                    regHouse = rs.getString("reg_house"),
                    regStreet = rs.getString("reg_street"),

                    comment = rs.getString("comment"),
                    status = ConclusionStatus.valueOf(rs.getString("status")),
                    userId = userId,

                    ud_back = rs.getString("ud_back"),
                    ud_front = rs.getString("ud_front"),
                    photo3x4 = rs.getString("photo3x4"),
                    school_diploma = rs.getString("school_diploma"),
                    ent_certificate = rs.getString("ent_certificate"),
                    form86 = rs.getString("form86"),
                    form63 = rs.getString("form63"),
                    flurography = rs.getString("flurography")
                )
            }, userId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun save(personalInfo: SavePersonalInfoRequest, areaId: Long, userId: Long) : Long? {
        val query = "INSERT INTO PersonalInfo(" +
            "first_name, middle_name, last_name, gender, birth_date, " +
            "given_date, given_place, iin, ud_number, nationality, " +
            "blood_group, citizenship, birth_place_id, mobile_phone, tel_phone, " +
            "fact_flat, fact_fraction, fact_house, fact_street, " +
            "reg_flat, reg_fraction, reg_house, reg_street, comment, status, user_id) VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, personalInfo.firstName)
                ps.setString(counter++, personalInfo.middleName)
                ps.setString(counter++, personalInfo.lastName)
                ps.setString(counter++, personalInfo.gender)
                ps.setTimestamp(counter++, Timestamp(personalInfo.birthDate.time))
                ps.setTimestamp(counter++, Timestamp(personalInfo.givenDate.time))
                ps.setString(counter++, personalInfo.givenPlace)
                ps.setString(counter++, personalInfo.iin)
                ps.setString(counter++, personalInfo.ud_number)
                ps.setString(counter++, personalInfo.nationality)
                ps.setString(counter++, personalInfo.blood_group)
                ps.setString(counter++, personalInfo.citizenship)
                ps.setLong(counter++, areaId)
                ps.setString(counter++, personalInfo.mobilePhone)
                ps.setString(counter++, personalInfo.telPhone)
                ps.setString(counter++, personalInfo.factFlat)
                ps.setString(counter++, personalInfo.factFraction)
                ps.setString(counter++, personalInfo.factHouse)
                ps.setString(counter++, personalInfo.factStreet)
                ps.setString(counter++, personalInfo.regFlat)
                ps.setString(counter++, personalInfo.regFraction)
                ps.setString(counter++, personalInfo.regHouse)
                ps.setString(counter++, personalInfo.regStreet)
                ps.setString(counter++, "")
                ps.setString(counter++, ConclusionStatus.WAITING_FOR_RESPONSE.name)
                ps.setLong(counter, userId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun update(personalInfo: SavePersonalInfoRequest, areaId: Long, userId: Long, id: Long) {
        val query = "UPDATE PersonalInfo SET " +
            "first_name=?, middle_name=?, last_name=?, gender=?, birth_date=?, " +
            "given_date=?, given_place=?, iin=?, ud_number=?, nationality=?," +
            "blood_group=?, citizenship=?, birth_place_id=?, mobile_phone=?, tel_phone=?," +
            "fact_flat=?, fact_fraction=?, fact_house=?, fact_street=?, reg_flat=?," +
            "reg_fraction=?, reg_house=?, reg_street=?, comment=?, status=?, user_id=? WHERE id=?"

        jdbcTemplate.update(query,
            {ps ->
                var counter = 1
                ps.setString(counter++, personalInfo.firstName)
                ps.setString(counter++, personalInfo.middleName)
                ps.setString(counter++, personalInfo.lastName)
                ps.setString(counter++, personalInfo.gender)
                ps.setTimestamp(counter++, Timestamp(personalInfo.birthDate.time))
                ps.setTimestamp(counter++, Timestamp(personalInfo.givenDate.time))
                ps.setString(counter++, personalInfo.givenPlace)
                ps.setString(counter++, personalInfo.iin)
                ps.setString(counter++, personalInfo.ud_number)
                ps.setString(counter++, personalInfo.nationality)
                ps.setString(counter++, personalInfo.blood_group)
                ps.setString(counter++, personalInfo.citizenship)
                ps.setLong(counter++, areaId)
                ps.setString(counter++, personalInfo.mobilePhone)
                ps.setString(counter++, personalInfo.telPhone)
                ps.setString(counter++, personalInfo.factFlat)
                ps.setString(counter++, personalInfo.factFraction)
                ps.setString(counter++, personalInfo.factHouse)
                ps.setString(counter++, personalInfo.factStreet)
                ps.setString(counter++, personalInfo.regFlat)
                ps.setString(counter++, personalInfo.regFraction)
                ps.setString(counter++, personalInfo.regHouse)
                ps.setString(counter++, personalInfo.regStreet)
                ps.setString(counter++, "")
                ps.setString(counter++, ConclusionStatus.WAITING_FOR_RESPONSE.name)
                ps.setLong(counter++, userId)
                ps.setLong(counter, id)
            })
    }
}