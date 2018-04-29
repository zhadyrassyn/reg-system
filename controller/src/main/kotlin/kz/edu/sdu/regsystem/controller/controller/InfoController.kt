package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.register.InfoRegister
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class InfoController(val infoRegister: InfoRegister) {

    @GetMapping("/cities")
    fun getCities() = infoRegister.getCities()

    @GetMapping("/cities/{id}/schools")
    fun getSchoolsByCity(@PathVariable("id") cityId: Long) = infoRegister.getSchools(cityId)

    @GetMapping("/areas")
    fun getAreas() = infoRegister.getAreas()

    @GetMapping("/faculties")
    fun getFaculties() = infoRegister.getFaculties()

    @GetMapping("/faculties/{id}/specializations")
    fun getSpecializations(@PathVariable("id") facultyId: Long) = infoRegister.getSpecializations(facultyId)
}