package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/moderator")
class ModeratorController(
    val moderatorRegister: ModeratorRegister
) {

    @GetMapping
    @RequestMapping("/students")
    fun getStudents(@RequestParam("currentPage")currentPage: Int,
                    @RequestParam("perPage")perPage: Int) = moderatorRegister.getStudents(currentPage, perPage)
}