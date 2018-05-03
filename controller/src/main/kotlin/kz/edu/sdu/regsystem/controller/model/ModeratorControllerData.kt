package kz.edu.sdu.regsystem.controller.model

import javax.management.monitor.StringMonitor

data class GetStudentsResponse(
    val id: Long = 0,
    val firstName: String = "",
    val middleName: String? = "",
    val lastName: String = "",
    val email: String = "",
    val city: String = "",
    val school: String = "",
    val birthDate: String = "",
    val userStatus: String = "ACTIVE"
)

data class GetStudentInfoResponse(
    var id: Long = -1,
    var firstName: String = "",
    var middleName: String? = "",
    var lastName: String = "",
    var email: String = "",
    var city: String = "",
    var school: String = "",
    var birthDate: String = "",
    var generalInfoStatus: String = "",
    var generalInfoComment: String = "",
    var documentsComment: String = "",
    var documents: List<DocumentInfoResposne> = ArrayList()
)

data class DocumentInfoResposne(
    val id: Long,
    val type: String,
    val status: String,
    val url: String
)

data class EditGeneralInfORequest(
    val comment: String? = null,
    val status: String
)

data class SaveCommentForDocumentsRequest(
    val comment: String
)

data class FetchTotalAmountOfStudentsResponse(
    var total: Int = 0
)

data class FetchPersonalInfoResponse(
    val id: Long,
    val firstName: String,
    val middleName: String? = "",
    val lastName: String,
    val gender: String,
    val birthDate: String,
    val givenDate: String,
    val givenPlace: String,
    val iin: String,
    val ud_number: String,
    val mobilePhone: String,
    val telPhone: String?,
    val nationality: String,
    val birthPlace: String,
    val blood_group: String? = "",
    val citizenship: String,
    val factStreet: String,
    val factHouse: String,
    val factFraction: String? = "",
    val factFlat: String? = "",
    val regStreet: String,
    val regHouse: String,
    val regFraction: String? = "",
    val regFlact: String? = "",

    val ud_front: String? = null,
    val ud_back: String? = null,
    val photo3x4: String? = null,

    val comment: String,
    val status: String
)