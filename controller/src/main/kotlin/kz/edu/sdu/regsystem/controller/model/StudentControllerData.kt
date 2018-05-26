package kz.edu.sdu.regsystem.controller.model

import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class SavePersonalInfoRequest(
    val id: Long? = null,
    var firstName: String,
    var middleName: String? = null,
    var lastName: String,
    var gender: String,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var birthDate: Date,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var givenDate: Date,
    var givenPlace: String,
    var iin: String,
    var ud_number: String,

    var mobilePhone: String,
    var telPhone: String? = null,
    var nationality: String,

    var birthPlace: Long? = null,
    var birthPlaceCustom: String? = null,
    var blood_group: String? = null,
    var citizenship: String,
    var factFlat: String? = null,
    var factFraction: String? = null,
    var factHouse: String,
    var factStreet: String,

    var regFlat: String? = null,
    var regFraction: String? = null,
    var regHouse: String,
    var regStreet: String
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

    val ud_front: String? = "",
    val ud_back: String? = "",
    val photo3x4: String? = "",

    val comment: String? = "",
    val status: String = ""
)

data class Document(
    val name: String
)

data class SaveEducationInfoRequestData(
    val id: Long? = null,
    val educationArea: Long? = null,
    val city: Long? = null,
    val another_cityVillage: String? = null,
    val school: Long? = null,
    val customSchool: String? = null,
    val ent_amount: Long,
    val ent_certificate_number: String,
    val ikt: String,
    val faculty: Long,
    val speciality: Long,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val school_finish: Date
)

data class GetEducationInfoResponseData(
    val id: Long? = null,
    val educationArea: AreaData? = null,
    val city: CityData? = null,
    val another_cityVillage: String? = null,
    val school: SchoolData? = null,
    val customSchool: String? = null,
    val ent_amount: Long? = null,
    val ent_certificate_number: String? = null,
    val ikt: String? = null,
    val faculty: GetFacultiesResponseData? = null,
    val speciality: GetSpecialtyResponseData? = null,
    val school_finish: String? = null,
    val comment: String? = "",
    val status: String = "",

    val schoolDiploma: String? = "",
    val entCertificate: String? = ""
)

data class GetMedicalInfoResponseData(
    val comment: String? = "",
    val status: String = "",
    val form63: String? = "",
    val form86: String? = "",
    val flurography: String? = ""
)