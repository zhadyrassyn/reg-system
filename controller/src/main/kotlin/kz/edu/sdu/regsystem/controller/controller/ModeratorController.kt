package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.EditGeneralInfORequest
import kz.edu.sdu.regsystem.controller.model.SaveCommentForDocumentsRequest
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/moderator")
class ModeratorController(
    val moderatorRegister: ModeratorRegister
) {

    @GetMapping("/students")
    fun getStudents(@RequestParam("currentPage")currentPage: Int,
                    @RequestParam("perPage")perPage: Int) = moderatorRegister.getStudents(currentPage, perPage)

    @GetMapping("/students/{id}")
    fun getStudentInfo(@PathVariable("id") id: Long) = moderatorRegister.getStudentInfo(id)

    @PostMapping("/students/{id}/editGeneralInfo")
    @ResponseStatus(HttpStatus.OK)
    fun editGeneralInfO(@PathVariable("id") id: Long,
                        @RequestBody request: EditGeneralInfORequest) = moderatorRegister.editGeneralInfo(id, request)

    @PostMapping("/students/{id}/documents/comment")
    @ResponseStatus(HttpStatus.OK)
    fun saveCommentForDocuments(@PathVariable("id") id: Long,
                                @RequestBody request: SaveCommentForDocumentsRequest) = moderatorRegister.saveCommentForDocuments(id, request)
}