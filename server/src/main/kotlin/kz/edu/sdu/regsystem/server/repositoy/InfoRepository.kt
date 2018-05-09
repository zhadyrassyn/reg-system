package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.Area
import kz.edu.sdu.regsystem.server.domain.City
import kz.edu.sdu.regsystem.server.domain.School
import kz.edu.sdu.regsystem.server.domain.enums.AreaType
import kz.edu.sdu.regsystem.server.domain.enums.SchoolStatusEnum
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class InfoRepository(val jdbcTemplate: JdbcTemplate) {

    /* GETTERS */
    fun getAreas(): List<Area> {
        val query = "SELECT * FROM AREA WHERE type='SYSTEM'"
        return jdbcTemplate.query(query, RowMapper { rs, rowNum ->
            Area(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                type = AreaType.SYSTEM
            )
        })
    }

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

    /* SAVERS */
    fun saveArea(area: Area) : Long {
        val query = "INSERT INTO AREA(name_ru, name_en, name_kk, type) VALUES (?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, area.nameRu)
                ps.setString(counter++, area.nameEn)
                ps.setString(counter++, area.nameKk)
                ps.setString(counter, area.type.name)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
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
        val query = "INSERT INTO SCHOOL(name_ru, name_en, name_kk, status, city) VALUES (?, ?, ?, ?, ?)"

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
        val query = "SELECT * FROM SCHOOL WHERE city=?"

        return jdbcTemplate.query(query, RowMapper { rs, _ ->
            School(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                status = SchoolStatusEnum.valueOf(rs.getString("status")),
                cityId = rs.getLong("city")
            )
        }, cityId)
    }

    fun getSchoolsByCityAndStatus(cityId: Long, status: String): List<School> {
        val query = "SELECT * FROM SCHOOL WHERE city=? AND status=?"

        return jdbcTemplate.query(query, RowMapper { rs, _ ->
            School(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                status = SchoolStatusEnum.valueOf(rs.getString("status")),
                cityId = rs.getLong("city")
            )
        },
            cityId,
            status)
    }
}