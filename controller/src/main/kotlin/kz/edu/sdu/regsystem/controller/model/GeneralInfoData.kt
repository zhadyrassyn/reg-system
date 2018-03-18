package kz.edu.sdu.regsystem.controller.model

import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class GeneralInfoData(
    val firstName: String,
    val middleName: String,
    val lastName: String,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthDate: Date,

    val cityId: Long,
    val schoolId: Long,
    val customSchool: String
)