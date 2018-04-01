package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.GetStudentsResponse
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
class ModeratorRegisterStandImpl(
    val db: Db
) : ModeratorRegister {

    override fun getStudents(currentPage: Int, perPage: Int): List<GetStudentsResponse> {
        val from = (currentPage-1) * perPage
        val to = currentPage + perPage

        return db.users.values
            .filter { db.userRoles[it.id] == RoleType.USER && it.userStatus == UserStatus.ACTIVE }
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