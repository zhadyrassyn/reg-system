package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.DocumentRegister
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/student")
class StudentController(
    val studentRegister: StudentRegister,
    val documentRegister: DocumentRegister
) {
    @PostMapping("/general/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveGeneralInfo(@RequestBody generalInfoData: GeneralInfoData,
                        @RequestHeader("Authorization") authToken: String) =
        studentRegister.saveGeneralInfo(
            authToken = authToken, generalInfoData = generalInfoData)

    @GetMapping("/general")
    fun getGeneralInfo(@RequestHeader("Authorization") authToken: String) =
        studentRegister.getGeneralInfo(authToken)


    @GetMapping("/document")
    fun getDocumentsStatus(@RequestHeader("Authorization") authToken: String) =
        documentRegister.fetchDocumentsStatus(authToken)

    @PostMapping("/document")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveDocument(@RequestHeader("Authorization") authToken: String,
                     @RequestParam("file") file: MultipartFile,
                     @RequestParam("type") documentType: DocumentType) = documentRegister.store(
        file = file, documentType = documentType, authToken = authToken)

}