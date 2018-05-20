package kz.edu.sdu.regsystem.server.domain

import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
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

data class PersonalInfo(
    var id: Long = -1,
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val gender: String,

    val birthDate: Date,
    val givenDate: Date,
    val givenPlace: String,
    val iin: String,
    val ud_number: String,
    val nationality: String,
    val blood_group: String? = null,
    val citizenship: String,

    val birthPlaceId: Long,

    val mobilePhone: String,
    val telPhone: String? = null,

    val factFlat: String? = null,
    val factFraction: String? = null,
    val factHouse: String,
    val factStreet: String,

    val regFlat: String? = null,
    val regFraction: String? = null,
    val regHouse: String,
    val regStreet: String,

    var comment: String = "",
    var status : ConclusionStatus = ConclusionStatus.WAITING_FOR_RESPONSE,

    val userId: Long
)

data class Document(
    var id: Long = -1,
    val ud_back: String,
    val ud_front: String,
    val photo3x4: String,
    val school_diploma: String,
    val ent_certificate: String,
    val form86: String,
    val form63: String,
    val flurography: String,
    val userId: Long
)

data class PersonalInfoDocument(
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val gender: String,

    val birthDate: Date,
    val givenDate: Date,
    val givenPlace: String,
    val iin: String,
    val ud_number: String,
    val nationality: String,
    val blood_group: String? = null,
    val citizenship: String,

    val birthPlaceId: Long,

    val mobilePhone: String,
    val telPhone: String? = null,

    val factFlat: String? = null,
    val factFraction: String? = null,
    val factHouse: String,
    val factStreet: String,

    val regFlat: String? = null,
    val regFraction: String? = null,
    val regHouse: String,
    val regStreet: String,

    var comment: String = "",
    var status : ConclusionStatus = ConclusionStatus.WAITING_FOR_RESPONSE,

    val userId: Long,

    val ud_back: String,
    val ud_front: String,
    val photo3x4: String,
    val school_diploma: String,
    val ent_certificate: String,
    val form86: String,
    val form63: String,
    val flurography: String
)

data class EducationInfo(
    var id: Long = -1,
    val areaId: Long,
    val cityId: Long,
    val schoolId: Long,
    val schoolFinish: Date,
    val entAmount: Int,
    val entCertificateNumber: String,
    val ikt: String,
    val facultyId: Long,
    val specialtyId: Long,
    val comment: String = "",
    val status: ConclusionStatus = ConclusionStatus.WAITING_FOR_RESPONSE,
    val userId: Long
)