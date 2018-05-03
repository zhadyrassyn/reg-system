package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.Area
import kz.edu.sdu.regsystem.stand.model.enums.DocumentStatus
import kz.edu.sdu.regsystem.stand.model.enums.GeneralInfoStatus
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import kz.edu.sdu.regsystem.stand.model.exceptions.CityDoesNotExistException
import kz.edu.sdu.regsystem.stand.model.exceptions.SchoolDoesNotExistException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
class ModeratorRegisterStandImpl(
    val db: Db
) : ModeratorRegister {
    override fun fetchTotalAmountOfStudents(text: String): FetchTotalAmountOfStudentsResponse {
        val response = FetchTotalAmountOfStudentsResponse()
        response.total = if (Objects.isNull(text)) {
            db.users.values
                .filter { db.userRoles[it.id] == RoleType.USER && it.userStatus == UserStatus.ACTIVE }.size
        } else {
            db.users.values
                .filter {
                    db.userRoles[it.id] == RoleType.USER
                        && it.userStatus == UserStatus.ACTIVE
                        && (
                        it.id.toString().contains(text, ignoreCase = true)
                            || it.firstName.contains(text, ignoreCase = true)
                            || it.middleName.contains(text, ignoreCase = true)
                            || it.lastName.contains(text, ignoreCase = true)
                            || db.cities[it.cityId]!!.name.contains(text, ignoreCase = true)
                            || db.cities[it.cityId]!!.schools.firstOrNull { school -> school.id == it.schoolId }!!.nameRu.contains(text, ignoreCase = true)
                            || dateToStringForm(it.birthDate).contains(text, ignoreCase = true)
                        )

                }.size
        }

        return response
    }

    override fun changeDocumentStatus(id: Long, documentId: Long, status: String) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        val document = user.documents.values.firstOrNull {
            it.id == documentId
        } ?: throw BadRequestException("Document with $documentId does not exist")

        document.documentStatus = DocumentStatus.valueOf(status)
    }

    override fun saveCommentForDocuments(id: Long, request: SaveCommentForDocumentsRequest) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        user.documentsComment = request.comment
    }

    override fun editGeneralInfo(id: Long, request: EditGeneralInfORequest) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        user.generalInfoStatusDto.comment = request.comment
        user.generalInfoStatusDto.status = GeneralInfoStatus.valueOf(request.status)
    }

    override fun getStudentInfo(id: Long): GetStudentInfoResponse {
        val response = GetStudentInfoResponse()

        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        return GetStudentInfoResponse()
//        val personalInfo = user.personalInfo
//        if(personalInfo == null) {
//            return GetStudentInfoResponse()
//        } else {
//            val cityId: Long? = user.educationInfo?.city?.id
//            val city: String
//            val school: String
//            if(cityId == null) {
//                city = ""
//                school = ""
//            } else {
//                val area = db.areas[user.educationInfo!!.area.id]
//                val cityData = area!!.cities[cityId]
//                city = cityData!!.nameRu
//                school = cityData.schools[user.educationInfo!!.school.id]!!.nameRu
//            }
//            return GetStudentInfoResponse(
//                id = id,
//                firstName = personalInfo.firstName,
//                middleName = personalInfo.middleName,
//                lastName = personalInfo.lastName,
//                birthDate = dateToStringForm(personalInfo.birthDate),
//                city = city,
//                school = school
//            )
//        }
    }

    override fun getStudents(text: String, currentPage: Int, perPage: Int): List<GetStudentsResponse> {
        val filteredUsers = if (Objects.isNull(text)) {
            db.users.values
                .filter { db.userRoles[it.id] == RoleType.USER && it.userStatus == UserStatus.ACTIVE }
        } else {
            db.users.values
                .filter {
                    db.userRoles[it.id] == RoleType.USER
                        && it.userStatus == UserStatus.ACTIVE
//                        && (
//                        it.id.toString().contains(text, ignoreCase = true)
//                            || it.firstName.contains(text, ignoreCase = true)
//                            || it.middleName.contains(text, ignoreCase = true)
//                            || it.lastName.contains(text, ignoreCase = true)
//                            || db.cities[it.cityId]!!.name.contains(text, ignoreCase = true)
//                            || db.cities[it.cityId]!!.schools.firstOrNull { school -> school.id == it.schoolId }!!.nameRu.contains(text, ignoreCase = true)
//                            || dateToStringForm(it.birthDate).contains(text, ignoreCase = true)
//                        )

                }
        }

        val total = filteredUsers.size
        val from = (currentPage - 1) * perPage
        val to = if (currentPage * perPage <= total) currentPage * perPage else total

        return filteredUsers
            .subList(from, to)
            .map {
                if(it.personalInfo == null || it.educationInfo == null) {
                    GetStudentsResponse(id = it.id)
                } else {
                    val city: String
                    val school: String

                    val educationArea = db.areas[it.educationInfo?.area?.id]
                    if(educationArea == null) {
                        city = ""
                        school = ""
                    } else {
                        val cityData = educationArea!!.cities[it.educationInfo!!.city.id]
                        city = cityData!!.nameEn
                        school = cityData.schools[it.educationInfo!!.school.id]!!.nameRu
                    }

                    val personalInfo = it.personalInfo
                    GetStudentsResponse(
                        id = it.id,
                        firstName = personalInfo!!.firstName,
                        middleName = personalInfo.middleName,
                        lastName = personalInfo.lastName,
                        city = city,
                        school = school,
                        userStatus = it.userStatus.toString(),
                        birthDate = dateToStringForm(personalInfo.birthDate)
                    )
                }


            }
    }

    private fun dateToStringForm(birthDate: Date): String {
        val df = SimpleDateFormat("dd/MM/yyyy")

        return df.format(birthDate)
    }

}