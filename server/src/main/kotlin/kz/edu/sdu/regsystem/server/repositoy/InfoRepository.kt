package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.City
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class InfoRepository(val jdbcTemplate: JdbcTemplate) {
    fun getCities() : List<City> {
        val query = "SELECT * FROM CITY"
        return jdbcTemplate.query(query, { rs, rowNum ->
            City(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk")
            )

        })
    }

    fun saveCity(city: City) : Long {
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
}