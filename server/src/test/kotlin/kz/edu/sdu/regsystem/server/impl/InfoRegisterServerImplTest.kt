package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.server.domain.City
import kz.edu.sdu.regsystem.server.domain.School
import kz.edu.sdu.regsystem.server.domain.enums.SchoolStatusEnum
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.annotations.Test
import java.util.*

@SpringBootTest
class InfoRegisterServerImplTest : AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var infoRegisterServerImpl: InfoRegisterServerImpl

    @Autowired
    lateinit var infoRepository: InfoRepository

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    var cities: List<City> = ArrayList<City>()
    var schools: List<School> = ArrayList<School>()

    fun initDb() {
        jdbcTemplate.execute("DELETE FROM USERS")
        jdbcTemplate.execute("DELETE FROM SCHOOL")
        jdbcTemplate.execute("DELETE FROM CITY")

        val c1 = City(
            id = -1,
            nameRu = "ru1",
            nameEn = "en1",
            nameKk = "kk1")
        val c2 = City(id = -1,
            nameRu = "ru2",
            nameEn = "en2",
            nameKk = "kk2")

        cities = Arrays.asList(c1, c2)
        cities.forEach {
            val id = infoRepository.saveCity(it)
            it.id = id
        }

        val s1 = School(
            id = -1,
            nameRu = "s1ru",
            nameEn = "s1en",
            nameKk = "s1kk",
            status = SchoolStatusEnum.ACTIVE,
            cityId = cities[0].id)

        val s2 = School(
            id = -1,
            nameRu = "s2ru",
            nameEn = "s2en",
            nameKk = "s2kk",
            status = SchoolStatusEnum.NONACTIVE,
            cityId = cities[0].id)

        schools = Arrays.asList(s1, s2)
        for(i in 0..schools.size-1) {
            val schoolId = infoRepository.saveSchool(schools[i])
            schools[i].id = schoolId
        }

    }

    @Test
    fun getCities() {
        initDb()

        //
        //
        val response = infoRegisterServerImpl.getCities()
        //
        //

        assertNotNull(response)
        assertEquals(response.size, cities.size)

        for (i in 0..response.size-1) {
            assertEquals(response[i].id, cities[i].id)
            assertEquals(response[i].nameEn, cities[i].nameEn)
            assertEquals(response[i].nameRu, cities[i].nameRu)
            assertEquals(response[i].nameKk, cities[i].nameKk)
        }
    }

    @Test
    fun getSchoolsByCity() {
        initDb()

        //
        //
        val response = infoRegisterServerImpl.getSchools(cities[0].id)
        //
        //

        assertNotNull(response)
        assertEquals(response.size, 1)

        assertEquals(response[0].id, schools[0].id)
        assertEquals(response[0].nameRu, schools[0].nameRu)
        assertEquals(response[0].nameEn, schools[0].nameEn)
        assertEquals(response[0].nameKk, schools[0].nameKk)
        assertEquals(response[0].schoolStatus, schools[0].status.name)
    }

}