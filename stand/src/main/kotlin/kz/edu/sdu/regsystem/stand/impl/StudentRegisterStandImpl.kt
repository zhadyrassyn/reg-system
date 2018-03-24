package kz.edu.sdu.regsystem.stand.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
import kz.edu.sdu.regsystem.controller.model.CityData
import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.GetGeneralInfoResponseData
import kz.edu.sdu.regsystem.controller.model.SchoolData
import kz.edu.sdu.regsystem.controller.model.enums.AccessType
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.School
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class StudentRegisterStandImpl(
    val db: Db,
    val env: Environment
) : StudentRegister{

    override fun saveGeneralInfo(authToken: String, generalInfoData: GeneralInfoData) {

        println("INPUT $generalInfoData")
        val token = authToken.substring(7)
        val jwtKey = env.getProperty("jwtKey")

        val email = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).body.subject
            ?: throw SignatureException("Cannot parse jwt.")


        val user = db.users.values.firstOrNull { it -> it.email == email } ?:
            throw UserDoesNotExistsException("User does not exist")

        user.firstName = generalInfoData.firstName
        user.middleName = generalInfoData.middleName
        user.lastName = generalInfoData.lastName
        user.birthDate = generalInfoData.birthDate
        user.cityId = generalInfoData.cityId

        if(generalInfoData.schoolId == (-1).toLong()) {
            val newSchool = School(db.longCounter.incrementAndGet(), generalInfoData.customSchool)
            db.cities[user.cityId]!!.schools.add(newSchool)
            user.schoolId = newSchool.id
        } else {
            user.schoolId = generalInfoData.schoolId
        }
    }

    override fun getGeneralInfo(authToken: String) : GetGeneralInfoResponseData {
        val token = authToken.substring(7)
        val jwtKey = env.getProperty("jwtKey")

        val email = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).body.subject
            ?: throw SignatureException("Cannot parse jwt.")

        val user = db.users.values.firstOrNull { it -> it.email == email } ?:
        throw UserDoesNotExistsException("User does not exist")

        //check if user not filled general info form before
        if(user.cityId == (-1).toLong() && user.schoolId == (-1).toLong()) {
            return GetGeneralInfoResponseData()
        } else {
            println("Schols ${db.cities[user.cityId]!!.schools}")
            val cityDto = db.cities[user.cityId] ?: throw BadRequestException("City Does Not Exist")
            val schoolDto = db.cities[user.cityId]!!.schools.firstOrNull { it.id == user.schoolId } ?: throw BadRequestException("School Does Not Exist")

            val responseData = GetGeneralInfoResponseData(
                firstName = user.firstName,
                middleName = user.middleName,
                lastName = user.lastName,
                birthDate = toDate(user.birthDate),
                city = CityData(
                    value = cityDto.id,
                    label = cityDto.name
                ),
                accessType = AccessType.EDIT
            )

            if(schoolDto.schoolStatus == SchoolStatus.NONACTIVE) {
                responseData.customSchool = schoolDto.name
            } else {
                responseData.school = SchoolData(
                    value = schoolDto.id,
                    label = schoolDto.name,
                    schoolStatus = schoolDto.schoolStatus.toString()
                )
            }
            return responseData
        }

    }

    private fun toDate(birthDate: Date) : String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(birthDate)
    }

}