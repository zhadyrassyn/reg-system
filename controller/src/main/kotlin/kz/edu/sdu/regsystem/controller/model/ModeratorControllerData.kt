package kz.edu.sdu.regsystem.controller.model

data class GetStudentsResponse(
    val id: Long = 0,
    val firstName: String = "",
    val middleName: String? = "",
    val lastName: String = "",
    val email: String = "",
    val iin: String = "",
    val gender: String = "",
    val generalStatus : String = ""
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
    val birthPlace: AreaData? = null,
    val blood_group: String? = null,
    val citizenship: String? = null,
    val factStreet: String? = null,
    val factHouse: String? = null,
    val factFraction: String? = null,
    val factFlat: String? = null,
    val regStreet: String? = null,
    val regHouse: String? = null,
    val regFraction: String? = null,
    val regFlact: String? = null,

    val ud_front: String? = "",
    val ud_back: String? = "",
    val photo3x4: String? = "",

    val comment: String? = "",
    val status: String? = "WAITING_FOR_RESPONSE"
)

data class FetchEducationInfoResponse (
    val id: Long = -1,
    val educationArea: AreaData? = null,
    val city: CityData? = null,
    val school: SchoolData? = null,

    val ent_amount: String? = "",
    val ent_certificate_number: String? = "",
    val ikt: String? = "",
    val faculty: GetFacultiesResponseData? = null,
    val speciality: GetSpecialtyResponseData? = null,

    val school_finish: String? = "",
    val schoolDiploma: String? = null,
    val entCertificate: String? = null,

    val comment: String? = "",
    val status: String? = "WATING_FOR_RESPONSE"
)

data class FetchMedicalInfoResponse (
    val id: Long = -1,
    val comment: String? = "",
    val status: String? = "WATING_FOR_RESPONSE",

    val form63: String? = "",
    val form86: String? = "",
    val flurography: String? = ""
)


