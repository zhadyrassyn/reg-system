package kz.edu.sdu.regsystem.stand.model

import java.util.*

data class EducationInfo(
    val id: Long,
    val area: Area,
    val city: UserCity,
    val school: School,
    val ent_amount: Long,
    val ent_certificate_number: String,
    val ikt: String,
    val faculty: UserFaculty,
    val speciality: Speciality,
    val school_finish: Date
)