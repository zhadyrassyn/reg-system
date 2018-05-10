package kz.edu.sdu.regsystem.server.domain

import kz.edu.sdu.regsystem.server.domain.enums.ExistType
import kz.edu.sdu.regsystem.server.domain.enums.RoleType
import kz.edu.sdu.regsystem.server.domain.enums.UserStatus
import java.util.*

data class Area(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val type: ExistType = ExistType.SYSTEM
)

data class City(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val type: ExistType = ExistType.SYSTEM,
    val areaId: Long
)

data class School(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val type: ExistType = ExistType.SYSTEM,
    val cityId: Long
)

data class Faculty(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String
)

data class Specialty(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val faculty_id: Long
)

data class User(
    var id: Long = -1,
    val email: String,
    val password: String,
    val regDate: Date,
    var status: UserStatus,
    val role: RoleType
)

data class VerificationToken(
    var id: Long = -1,
    val token: String,
    val createdDate: Date,
    val userId: Long
)