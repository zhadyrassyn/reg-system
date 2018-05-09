package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.server.domain.Area
import kz.edu.sdu.regsystem.server.domain.City
import kz.edu.sdu.regsystem.server.domain.School
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.annotations.Test
import kotlin.collections.ArrayList

@SpringBootTest
class InfoRegisterImplTest : AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var infoRegisterImpl: InfoRegisterImpl

    @Autowired
    lateinit var infoRepository: InfoRepository

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    var areas: List<Area> = ArrayList()
    var cities: List<City> = ArrayList()
    var schools: List<School> = ArrayList()

    fun clearDb() {
        jdbcTemplate.execute("DELETE FROM SCHOOL")
        jdbcTemplate.execute("DELETE FROM CITY")
        jdbcTemplate.execute("DELETE FROM AREA")
//        jdbcTemplate.execute("DELETE FROM SPECIALTY")
//        jdbcTemplate.execute("DELETE FROM FACULTY")
//        jdbcTemplate.execute("DELETE FROM USERS")
//        jdbcTemplate.execute("DELETE FROM SCHOOL")
//        jdbcTemplate.execute("DELETE FROM CITY")
//        jdbcTemplate.execute("DELETE FROM AREA")
    }

    fun initDb() {
        clearDb()

        val a1 = Area(
            nameRu = "Акмолинская область",
            nameEn = "Akmolinskaya oblast",
            nameKk = "Акмолинская область"
        )

        a1.id = infoRepository.saveArea(a1)

        val a1c1 = City(
            nameRu = "Комсомольское",
            nameEn = "Komsomolskoe",
            nameKk = "Комсомольское",
            areaId = a1.id
        )

        a1c1.id = infoRepository.saveCity(a1c1)

        val a1c1s1 = School(
            nameRu = "Шалкар НУ",
            nameEn = "Shalkar NU",
            nameKk = "Шалкар НУ",
            cityId = a1c1.id
        )

        val a1c1s2 = School(
            nameRu = "Шалкар БИЛ",
            nameEn = "Shalkar NU",
            nameKk = "Шалкар БИЛ",
            cityId = a1c1.id
        )

        a1c1s1.id = infoRepository.saveSchool(a1c1s1)
        a1c1s2.id = infoRepository.saveSchool(a1c1s2)

        schools = arrayListOf(a1c1s1, a1c1s2)

        val a1c2 = City(
            nameRu = "Астана",
            nameEn = "Astana",
            nameKk = "Астана",
            areaId = a1.id
        )
        a1c2.id = infoRepository.saveCity(a1c2)

        cities = arrayListOf(a1c1, a1c2)

        val a2 = Area(
            nameRu = "Актюбинская область",
            nameEn = "Akubinskaya oblast",
            nameKk = "Актюбинская область"
        )
        a2.id = infoRepository.saveArea(a2)

        areas = arrayListOf(a1, a2)
    }

    @Test
    fun getAreas() {
        initDb();

        //
        //
        val response = infoRegisterImpl.getAreas()
        //
        //

        assertNotNull(response)
        assertEquals(response.size, areas.size)

        for (i in 0..response.size - 1) {
            assertEquals(response[i].id, areas[i].id)
            assertEquals(response[i].nameRu, areas[i].nameRu)
            assertEquals(response[i].nameEn, areas[i].nameEn)
            assertEquals(response[i].nameKk, areas[i].nameKk)
        }
    }

    @Test
    fun getCities() {
        initDb()

        //
        //
        val response = infoRegisterImpl.getCitiesAndVillages(areas[0].id)
        //
        //

        assertNotNull(response)
        assertEquals(response.size, cities.size)

        for (i in 0..response.size - 1) {
            assertEquals(response[i].id, cities[i].id)
            assertEquals(response[i].nameEn, cities[i].nameEn)
            assertEquals(response[i].nameRu, cities[i].nameRu)
            assertEquals(response[i].nameKk, cities[i].nameKk)
            assertEquals(response[i].areaId, cities[i].areaId)
        }
    }

    //
    @Test
    fun getSchools() {
        initDb()

        //
        //
        val response = infoRegisterImpl.getSchools(0, cities[0].id)
        //
        //

        assertNotNull(response)
        assertEquals(response.size, schools.size)

        for (i in 0..response.size - 1) {
            assertEquals(response[i].id, schools[i].id)
            assertEquals(response[i].nameEn, schools[i].nameEn)
            assertEquals(response[i].nameRu, schools[i].nameRu)
            assertEquals(response[i].nameKk, schools[i].nameKk)
            assertEquals(response[i].cityId, schools[i].cityId)
        }
    }

}