package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.DocumentStorageRegister
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/student")
class StudentControlle(
    val studentRegister: StudentRegister,
    val documentStorageRegister: DocumentStorageRegister
) {
    @PostMapping("/general")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveGeneralInfo(@RequestBody generalInfoData: GeneralInfoData,
                        @RequestHeader("Authorization") authToken: String) =
        studentRegister.saveGeneralInfo(
            authToken = authToken, generalInfoData = generalInfoData)

    @PostMapping("/document")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveDocument(@RequestHeader("Authorization") authToken: String,
                     @RequestParam("file") file: MultipartFile,
                     @RequestParam("type") documentType: DocumentType) = documentStorageRegister.store(
        file = file, documentType = documentType, authToken = authToken);
}