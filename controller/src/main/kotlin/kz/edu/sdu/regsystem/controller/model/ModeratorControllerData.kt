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