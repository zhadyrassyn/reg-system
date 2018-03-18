package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/student")
class StudentControlle(
    val studentRegister: StudentRegister
) {
    @PostMapping("/general")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveGeneralInfo(@RequestBody generalInfoData: GeneralInfoData) =
        studentRegister.saveGeneralInfo(generalInfoData)
}