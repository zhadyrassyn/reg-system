package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.DocumentInfoResposne
import kz.edu.sdu.regsystem.controller.model.EditGeneralInfORequest
import kz.edu.sdu.regsystem.controller.model.GetStudentInfoResponse
import kz.edu.sdu.regsystem.controller.model.GetStudentsResponse
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
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

        val city = db.cities.values.firstOrNull { it.id == user.cityId } ?: throw CityDoesNotExistException("City with ${user.cityId} does not exist")
        val school = city.schools.firstOrNull { school -> school.id == user.schoolId } ?: throw SchoolDoesNotExistException("Cannot find school with id ${user.schoolId}")
        val documents = user.documents.values
            .map { DocumentInfoResposne(
                id = it.id,
                type = it.documentType.name,
                status = it.documentStatus.name,
                url = if(it.path == null) "default.png" else it.path!!.fileName.toString())
            }

        response.id = user.id
        response.firstName = user.firstName
        response.middleName = user.middleName
        response.lastName = user.lastName
        response.email = user.email
        response.city = city.name
        response.school = school.name
        response.birthDate = dateToStringForm(user.birthDate)
        response.generalInfoStatus = user.generalInfoStatusDto.status.name
        response.generalInfoComment = user.generalInfoStatusDto.comment!!
        response.documents = documents

        return response
    }

    override fun getStudents(currentPage: Int, perPage: Int): List<GetStudentsResponse> {
        val filteredUsers = db.users.values
            .filter { db.userRoles[it.id] == RoleType.USER && it.userStatus == UserStatus.ACTIVE }

        val total = filteredUsers.size
        val from = (currentPage-1) * perPage
        val to = if (currentPage * perPage <= total) currentPage * perPage else total

        return filteredUsers
            .subList(from, to)
            .map {
                val city = db.cities[it.cityId] ?: throw Exception("Cannot find city with id ${it.cityId}")
                val school = city.schools.firstOrNull { school -> school.id == it.schoolId } ?: throw BadRequestException("Cannot find school with id ${it.schoolId}")

            GetStudentsResponse(
                    id = it.id,
                    firstName = it.firstName,
                    middleName = it.middleName,
                    lastName = it.lastName,
                    email = it.email,
                    city = city.name,
                    school = school.name,
                    userStatus = it.userStatus.toString(),
                    birthDate = dateToStringForm(it.birthDate)
                )
            }
    }

    private fun dateToStringForm(birthDate: Date) : String {
        val df = SimpleDateFormat("dd/MM/yyyy")

        return df.format(birthDate)
    }

}