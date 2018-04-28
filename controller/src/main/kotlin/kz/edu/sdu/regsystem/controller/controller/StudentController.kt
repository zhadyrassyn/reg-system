package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.SavePersonalInfoRequest
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.DocumentRegister
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/student")
class StudentController(
    val studentRegister: StudentRegister,
    val documentRegister: DocumentRegister
) {
    @PostMapping("/general/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveGeneralInfo(@RequestBody generalInfoData: GeneralInfoData,
                        @PathVariable("id") id: Long) =
        studentRegister.saveGeneralInfo(
            id = id, generalInfoData = generalInfoData)

    @GetMapping("/general/{id}")
    fun getGeneralInfo(@PathVariable("id") id: Long) =
        studentRegister.getGeneralInfo(id)

    @GetMapping("/document/{id}")
    fun getDocumentsStatus(@PathVariable("id") id: Long) =
        documentRegister.fetchDocumentsStatus(id)

    @PostMapping("/document/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveDocument(@PathVariable("id") id: Long,
                     @RequestParam("file") file: MultipartFile,
                     @RequestParam("type") documentType: DocumentType) =
        documentRegister.store(
        file = file, documentType = documentType, id = id)

    @PostMapping("/personal/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun savePersonalInfo(@RequestBody personalInfo: SavePersonalInfoRequest,
                         @PathVariable("id") id: Long) = studentRegister.savePersonalInfo(personalInfo, id)

    @GetMapping("/personal/{id}")
    fun getPersonalInfo(@PathVariable("id") id: Long) = studentRegister.getPersonalInfo(id)

}