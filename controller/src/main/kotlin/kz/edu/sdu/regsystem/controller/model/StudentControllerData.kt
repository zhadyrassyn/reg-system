package kz.edu.sdu.regsystem.controller.model

import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class SavePersonalInfoRequest(
    val id: Long? = null,
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val gender: String,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthDate: Date,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val givenDate: Date,
    val givenPlace: String,
    val iin: String,
    val ud_number: String,

    val mobilePhone: String,
    val telPhone: String? = null,
    val nationality: String,

    val birthPlace: Long? = null,
    val birthPlaceCustom: String? = null,
    val blood_group: String? = null,
    val citizenship: String,
    val factFlat: String? = null,
    val factFraction: String? = null,
    val factHouse: String,
    val factStreet: String,

    val regFlat: String? = null,
    val regFraction: String? = null,
    val regHouse: String,
    val regStreet: String
)

data class GetPersonalInfoResponse(
    val id: Long? = null,
    val firstName: String? = null,
    val middleName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,

    val birthDate: String? = null,
    val givenDate: String? = null,
    val givenPlace: String? = null,
    val iin: String? = null,
    val ud_number: String? = null,

    val mobilePhone: String? = null,
    val telPhone: String? = null,
    val nationality: String? = null,

    val birthPlace: Long? = null,
    val birthPlaceCustom: String? = null,
    val blood_group: String? = null,
    val citizenship: String? = null,
    val factFlat: String? = null,
    val factFraction: String? = null,
    val factHouse: String? = null,
    val factStreet: String? = null,

    val regFlat: String? = null,
    val regFraction: String? = null,
    val regHouse: String? = null,
    val regStreet: String? = null,

    val ud_front: String? = null,
    val ud_back: String? = null,
    val photo3x4: String? = null
)

data class Document(
    val name: String
)

data class SaveEducationInfoRequestData(
    val id: Long? = null,
    val educationArea: Long? = null,
    val another_cityVillage: String? = null,
    val city: Long? = null,
    val school: Long? = null,
    val customSchool: String? = null,
    val ent_amount: Long,
    val ent_certificate_number: String,
    val faculty: Long,
    val ikt: String,
    val speciality: Long,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val school_finish: Date? = null
)