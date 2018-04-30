package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.SaveEducationInfoRequestData
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
    @GetMapping("/document/{id}")
    fun getDocumentsStatus(@PathVariable("id") id: Long) =
        documentRegister.fetchDocumentsStatus(id)

//    @PostMapping("/document/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
//    fun saveDocument(@PathVariable("id") id: Long,
//                     @RequestParam("file") file: MultipartFile,
//                     @RequestParam("type") documentType: DocumentType) =
//        documentRegister.store(
//        file = file, documentType = documentType, id = id)

    @PostMapping("/personal/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun savePersonalInfo(@RequestBody personalInfo: SavePersonalInfoRequest,
                         @PathVariable("id") id: Long) = studentRegister.savePersonalInfo(personalInfo, id)

    @GetMapping("/personal/{id}")
    fun getPersonalInfo(@PathVariable("id") id: Long) = studentRegister.getPersonalInfo(id)

    @PostMapping("/personal/{id}/document")
    @ResponseStatus(HttpStatus.CREATED)
    fun savePersonalInfoDocument(@PathVariable("id")id: Long,
                                 @RequestParam("file")file: MultipartFile,
                                 @RequestParam("type") documentType: DocumentType) =
        studentRegister.savePersonalInfoDocument(id, file, documentType)

    @PostMapping("/education/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveEducationInfo(@RequestBody educationInfo: SaveEducationInfoRequestData,
                          @PathVariable("id")id: Long) =
        studentRegister.saveEducationInfo(educationInfo, id)

    @GetMapping("/education/{id}")
    fun getEducationInfo(@PathVariable("id") id: Long) = studentRegister.getEducationInfo(id)

    @PostMapping("/education/{id}/document")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveEducationInfoDocument(@PathVariable("id")id: Long,
                                  @RequestParam("file")file: MultipartFile,
                                  @RequestParam("type") documentType: DocumentType) =
        studentRegister.saveEducationInfoDocument(id, file, documentType)

}