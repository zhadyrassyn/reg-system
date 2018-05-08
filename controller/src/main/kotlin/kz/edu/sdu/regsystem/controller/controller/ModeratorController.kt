package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.EditGeneralInfORequest
import kz.edu.sdu.regsystem.controller.model.SaveCommentForDocumentsRequest
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/moderator")
class ModeratorController(
    val moderatorRegister: ModeratorRegister
) {

    @GetMapping("/students")
    fun getStudents(@RequestParam("text") text: String,
                    @RequestParam("currentPage") currentPage: Int,
                    @RequestParam("perPage") perPage: Int) = moderatorRegister.getStudents(text, currentPage, perPage)

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

    @PostMapping("/students/{id}/documents/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    fun changeDocumentStatus(@PathVariable("id") id: Long,
                             @PathVariable("documentId") documentId: Long,
                             @RequestParam("status") status: String) = moderatorRegister.changeDocumentStatus(id, documentId, status)

    @GetMapping("/students/total")
    fun fetchTotalAmountOfStudents(@RequestParam("text") text: String) = moderatorRegister.fetchTotalAmountOfStudents(text)

    @GetMapping("/students/{id}/personal")
    fun fetchPersonalInfo(@PathVariable("id") id: Long) = moderatorRegister.fetchPersonalInfo(id)

    @GetMapping("/students/{id}/education")
    fun fetchEducationInfo(@PathVariable("id") id: Long) = moderatorRegister.fetchEducationInfo(id)

    @PostMapping("/students/{id}/education/comment")
    @ResponseStatus(HttpStatus.OK)
    fun saveEducationComment(@PathVariable("id") id: Long,
                             @RequestBody request: EditGeneralInfORequest) = moderatorRegister.saveEducationComment(id, request)
}