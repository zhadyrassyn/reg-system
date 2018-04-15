package kz.edu.sdu.regsystem.controller.model

data class GetStudentsResponse(
    val id: Long,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val email: String,
    val city: String,
    val school: String,
    val birthDate: String,
    val userStatus: String
)

data class GetStudentInfoResponse(
    var id: Long = -1,
    var firstName: String = "",
    var middleName: String = "",
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