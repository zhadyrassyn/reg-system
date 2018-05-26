package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.*
import kz.edu.sdu.regsystem.server.domain.enums.ExistType
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class InfoRepository(val jdbcTemplate: JdbcTemplate) {

    /* GET ALL */
    fun getAreas(): List<Area> {
        val query = "SELECT * FROM AREA WHERE type='SYSTEM'"
        return jdbcTemplate.query(query, { rs, rowNum ->
            Area(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                type = ExistType.SYSTEM
            )
        })
    }

    fun getCities(): List<City> {
        val query = "SELECT * FROM CITY WHERE type='SYSTEM'"
        return jdbcTemplate.query(query, { rs, _ ->
            City(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                type = ExistType.SYSTEM,
                areaId = rs.getLong("area_id")
            )
        })
    }

    fun getSchools(): List<School> {
        val query = "SELECT * FROM SCHOOL WHERE type='SYSTEM'"
        return jdbcTemplate.query(query, { rs, _ ->
            School(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                type = ExistType.SYSTEM,
                cityId = rs.getLong("city_id")
            )
        })
    }

    fun getFaculties(): List<Faculty> {
        val query = "SELECT * FROM FACULTY"
        return jdbcTemplate.query(query, { rs, _ ->
            Faculty(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk")
            )
        })
    }

    fun getSpecialties(): List<Specialty> {
        val query = "SELECT * FROM SPECIALTY"
        return jdbcTemplate.query(query, { rs, _ ->
            Specialty(
                id = rs.getLong("id"),
                nameRu = rs.getString("name_ru"),
                nameEn = rs.getString("name_en"),
                nameKk = rs.getString("name_kk"),
                faculty_id = rs.getLong("faculty_id")
            )
        })
    }

    /* GET BY ID */
    fun fetchArea(id: Long) : Area? {
        val query = "SELECT * FROM AREA WHERE id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                Area(
                    id = rs.getLong("id"),
                    nameRu = rs.getString("name_ru"),
                    nameEn = rs.getString("name_en"),
                    nameKk = rs.getString("name_kk"),
                    type = ExistType.valueOf(rs.getString("type"))
                )
            }, id)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun fetchCity(id: Long) : City? {
        val query = "SELECT * FROM CITY WHERE id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                City(
                    id = rs.getLong("id"),
                    nameRu = rs.getString("name_ru"),
                    nameEn = rs.getString("name_en"),
                    nameKk = rs.getString("name_kk"),
                    type = ExistType.valueOf(rs.getString("type")),
                    areaId = rs.getLong("area_id")
                )
            }, id)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun fetchSchool(id: Long) : School? {
        val query = "SELECT * FROM SCHOOL WHERE id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                School(
                    id = rs.getLong("id"),
                    nameRu = rs.getString("name_ru"),
                    nameEn = rs.getString("name_en"),
                    nameKk = rs.getString("name_kk"),
                    type = ExistType.valueOf(rs.getString("type")),
                    cityId = rs.getLong("city_id")
                )
            }, id)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun fetchFaculty(id: Long) : Faculty? {
        val query = "SELECT * FROM FACULTY WHERE id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                Faculty(
                    id = rs.getLong("id"),
                    nameRu = rs.getString("name_ru"),
                    nameEn = rs.getString("name_en"),
                    nameKk = rs.getString("name_kk")
                )
            }, id)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun fetchSpecialty(id: Long) : Specialty? {
        val query = "SELECT * FROM SPECIALTY WHERE id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                Specialty(
                    id = rs.getLong("id"),
                    nameRu = rs.getString("name_ru"),
                    nameEn = rs.getString("name_en"),
                    nameKk = rs.getString("name_kk"),
                    faculty_id = rs.getLong("faculty_id")
                )
            }, id)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
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
        val query = "INSERT INTO CITY(name_ru, name_en, name_kk, type, area_id) VALUES (?, ?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, city.nameRu)
                ps.setString(counter++, city.nameEn)
                ps.setString(counter++, city.nameKk)
                ps.setString(counter++, city.type.name)
                ps.setLong(counter, city.areaId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun saveSchool(school: School): Long {
        val query = "INSERT INTO SCHOOL(name_ru, name_en, name_kk, type, city_id) VALUES (?, ?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, school.nameRu)
                ps.setString(counter++, school.nameEn)
                ps.setString(counter++, school.nameKk)
                ps.setString(counter++, school.type.name)
                ps.setLong(counter, school.cityId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun saveFaculty(faculty: Faculty): Long {
        val query = "INSERT INTO FACULTY(name_ru, name_en, name_kk) VALUES (?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, faculty.nameRu)
                ps.setString(counter++, faculty.nameEn)
                ps.setString(counter, faculty.nameKk)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun saveSpecialty(specialty: Specialty): Long {
        val query = "INSERT INTO SPECIALTY(name_ru, name_en, name_kk, faculty_id) VALUES (?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, specialty.nameRu)
                ps.setString(counter++, specialty.nameEn)
                ps.setString(counter++, specialty.nameKk)
                ps.setLong(counter, specialty.faculty_id)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

//    fun getSchoolsByCity(cityId: Long): List<School> {
//        val query = "SELECT * FROM SCHOOL WHERE city=?"
//
//        return jdbcTemplate.query(query, RowMapper { rs, _ ->
//            School(
//                id = rs.getLong("id"),
//                nameRu = rs.getString("name_ru"),
//                nameEn = rs.getString("name_en"),
//                nameKk = rs.getString("name_kk"),
//                status = SchoolStatusEnum.valueOf(rs.getString("status")),
//                cityId = rs.getLong("city")
//            )
//        }, cityId)
//    }
//
//    fun getSchoolsByCityAndStatus(cityId: Long, status: String): List<School> {
//        val query = "SELECT * FROM SCHOOL WHERE city=? AND status=?"
//
//        return jdbcTemplate.query(query, RowMapper { rs, _ ->
//            School(
//                id = rs.getLong("id"),
//                nameRu = rs.getString("name_ru"),
//                nameEn = rs.getString("name_en"),
//                nameKk = rs.getString("name_kk"),
//                status = SchoolStatusEnum.valueOf(rs.getString("status")),
//                cityId = rs.getLong("city")
//            )
//        },
//            cityId,
//            status)
//    }
}