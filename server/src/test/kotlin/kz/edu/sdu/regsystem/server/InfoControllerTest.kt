package kz.edu.sdu.regsystem.server

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class InfoControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getCities() {
        val response = mockMvc.perform(get("/cities"))

    }

    @Test
    fun getSchooldByCity() {
    }

    @Test
    fun getInfoRegister() {
    }

}