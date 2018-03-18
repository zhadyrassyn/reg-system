package kz.edu.sdu.regsystem.stand.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.SchoolData
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.School
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class StudentRegisterStandImpl(
    val db: Db,
    val env: Environment
) : StudentRegister{
    override fun saveGeneralInfo(authToken: String, generalInfoData: GeneralInfoData) {
        val token = authToken.substring(7)
        val jwtKey = env.getProperty("jwtKey")

        val email = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).body.subject
            ?: throw SignatureException("Cannot parse jwt.")


        val user = db.users.values.firstOrNull { it -> it.email == email } ?:
            throw UserDoesNotExistsException("User does not exist")

        user.firstName = generalInfoData.firstName
        user.middleName = generalInfoData.middleName
        user.lastName = generalInfoData.lastName
        user.birthDate = generalInfoData.birthdate
        user.cityId = generalInfoData.cityId

        if(generalInfoData.schoolId == (-1).toLong()) {
            val newSchool = School(db.longCounter.incrementAndGet(), generalInfoData.customSchool)
            db.schools.put(newSchool.id, newSchool)
            user.schoolId = newSchool.id
        } else {
            user.schoolId = generalInfoData.schoolId
        }

    }

}