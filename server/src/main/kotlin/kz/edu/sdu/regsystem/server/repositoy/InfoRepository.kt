package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.City
import kz.edu.sdu.regsystem.server.domain.School
import kz.edu.sdu.regsystem.server.domain.enums.SchoolStatusEnum
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class InfoRepository(val jdbcTemplate: JdbcTemplate) {
    fun getCities(): List<City> {
        val query = "SELECT * FROM CITY"
        return jdbcTemplate.query(query, { rs, _ ->
            City(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk")
            )

        })
    }

    fun saveCity(city: City): Long {
        val query = "INSERT INTO CITY(name_ru, name_en, name_kk) VALUES (?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, city.nameRu)
                ps.setString(counter++, city.nameEn)
                ps.setString(counter, city.nameKk)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun saveSchool(school: School): Long {
        val query = "INSERT INTO SCHOOL(name_ru, name_en, name_kk, status, city_id) VALUES (?, ?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, school.nameRu)
                ps.setString(counter++, school.nameEn)
                ps.setString(counter++, school.nameKk)
                ps.setString(counter++, school.status.name)
                ps.setLong(counter, school.cityId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun getSchoolsByCity(cityId: Long): List<School> {
        val query = "SELECT * FROM SCHOOL WHERE city_id=?"

        return jdbcTemplate.query(query, RowMapper { rs, _ ->
            School(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                status = SchoolStatusEnum.valueOf(rs.getString("status")),
                cityId = rs.getLong("city_id")
            )
        }, cityId)
    }

    fun getSchoolsByCityAndStatus(cityId: Long, status: String): List<School> {
        val query = "SELECT * FROM SCHOOL WHERE city_id=? AND status=?"

        return jdbcTemplate.query(query, RowMapper { rs, _ ->
            School(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                status = SchoolStatusEnum.valueOf(rs.getString("status")),
                cityId = rs.getLong("city_id")
            )
        },
            cityId,
            status)
    }
}