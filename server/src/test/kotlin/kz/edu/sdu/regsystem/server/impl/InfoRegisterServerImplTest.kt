package kz.edu.sdu.regsystem.server

import kz.edu.sdu.regsystem.server.domain.City
import kz.edu.sdu.regsystem.server.impl.InfoRegisterServerImpl
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import org.testng.Assert.*;
import org.testng.annotations.Test;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeTest
import java.util.*

@SpringBootTest
class InfoRegisterServerImplTest : AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var infoRegisterServerImpl: InfoRegisterServerImpl

    @Autowired
    lateinit var infoRepository: InfoRepository

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun initDb() {
        jdbcTemplate.execute("DELETE FROM SCHOOL")
        jdbcTemplate.execute("DELETE FROM CITY")
        println("CLEARING ")
    }

    @Test
    fun getCities() {
        initDb()
        val c1 = City(id = -1, nameRu = "ru1", nameEn = "en1", nameKk = "kk1")
        val c2 = City(id = -1, nameRu = "ru2", nameEn = "en2", nameKk = "kk2")

        val cities: List<City> = Arrays.asList(c1, c2)

        cities.forEach {
            val id = infoRepository.saveCity(it)
            it.id = id
        }

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

    fun getSchooldByCity() {

    }

    @Test
    fun getInfoRegister() {
    }

}