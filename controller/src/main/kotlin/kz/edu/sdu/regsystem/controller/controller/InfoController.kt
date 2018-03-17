package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.register.InfoRegister
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cities")
class InfoController(val infoRegister: InfoRegister) {

    @GetMapping
    fun getCities() = infoRegister.getCities()

    @GetMapping("{id}/schools")
    fun getSchooldByCity(@PathVariable("id") cityId: Long) = infoRegister.getSchools(cityId)
}