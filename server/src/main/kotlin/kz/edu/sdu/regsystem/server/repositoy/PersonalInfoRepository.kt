package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.PersonalInfo
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

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
}