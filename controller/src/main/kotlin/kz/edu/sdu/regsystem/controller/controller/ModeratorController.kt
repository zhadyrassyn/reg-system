package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/moderator")
class ModeratorController(
    val moderatorRegister: ModeratorRegister
) {

    @GetMapping
    @RequestMapping("/students")
    fun getStudents() = moderatorRegister.getStudents()
}