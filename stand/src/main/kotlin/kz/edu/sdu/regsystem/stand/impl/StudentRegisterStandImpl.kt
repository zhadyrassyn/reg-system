package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.model.enums.AccessType
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.Area
import kz.edu.sdu.regsystem.stand.model.PersonalInfo
import kz.edu.sdu.regsystem.stand.model.School
import kz.edu.sdu.regsystem.stand.model.enums.AreaType
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.text.SimpleDateFormat
import java.util.*

@Service
class StudentRegisterStandImpl(
    val db: Db,
    val env: Environment
) : StudentRegister{
    override fun getPersonalInfo(id: Long): GetPersonalInfoResponse {
        val user = db.users.values.firstOrNull { it -> it.id == id } ?:
        throw UserDoesNotExistsException("User does not exist")

        val personalInfo = user.personalInfo ?: throw Exception("Cannot find personal info for user ${user.id}")

        // if birth place is custom value
        val birthPlaceCustom: String? =
            if (personalInfo.birthPlace.status == AreaType.CUSTOM)
                personalInfo.birthPlace.nameRu
            else
                null
        return GetPersonalInfoResponse(
            id = user.id,
            firstName = personalInfo.firstName,
            middleName = personalInfo.middleName,
            lastName = personalInfo.lastName,
            gender = personalInfo.gender,
            birthDate = personalInfo.birthDate,
            givenDate = personalInfo.givenDate,
            givenPlace = personalInfo.givenPlace,
            iin = personalInfo.iin,
            ud_number = personalInfo.ud_number,
            mobilePhone = personalInfo.mobilePhone,
            telPhone = personalInfo.telPhone,
            nationality = personalInfo.nationality,
            birthPlace = personalInfo.birthPlace.id,
            birthPlaceCustom = birthPlaceCustom,
            blood_group = personalInfo.blood_group,
            citizenship = personalInfo.citizenship,
            factFlat = personalInfo.factFlat,
            factFraction = personalInfo.factFraction,
            factHouse = personalInfo.factHouse,
            factStreet = personalInfo.factStreet,
            regFlat = personalInfo.regFlat,
            regFraction = personalInfo.regFraction,
            regHouse = personalInfo.regHouse,
            regStreet = personalInfo.regStreet,
            ud_front = Document(personalInfo.ud_front?.path.toString()),
            ud_back = Document(personalInfo.ud_back?.path.toString()),
            photo3x4 = Document(personalInfo.photo3x4?.path.toString())
        )
    }

    override fun savePersonalInfo(personalInfo: SavePersonalInfoRequest, id: Long) {
        val user = db.users.values.firstOrNull { it -> it.id == id } ?:
        throw UserDoesNotExistsException("User does not exist")

        //save new are if birthPlaceCustom field is not null
        var areaId = personalInfo.birthPlace
        if(Objects.isNull(personalInfo.birthPlaceCustom) && !StringUtils.isEmpty(personalInfo.birthPlaceCustom)) {
            val area = Area(
                id = db.longCounter.incrementAndGet(),
                nameKk = personalInfo.birthPlaceCustom!!,
                nameEn = personalInfo.birthPlaceCustom!!,
                nameRu = personalInfo.birthPlaceCustom!!,
                status = AreaType.CUSTOM
            )

            db.areas.put(area.id, area)
            areaId = area.id
        }

        user.personalInfo = PersonalInfo(
            firstName = personalInfo.firstName,
            middleName = personalInfo.middleName,
            lastName = personalInfo.lastName,
            gender = personalInfo.gender,
            birthDate = personalInfo.birthDate,
            givenDate = personalInfo.givenDate,
            givenPlace = personalInfo.givenPlace,
            iin = personalInfo.iin,
            ud_number = personalInfo.ud_number,
            mobilePhone = personalInfo.mobilePhone,
            telPhone = personalInfo.telPhone,
            nationality = personalInfo.nationality,
            birthPlace = db.areas.values.first { it.id == areaId },
            blood_group = personalInfo.blood_group,
            citizenship = personalInfo.citizenship,
            factFlat = personalInfo.factFlat,
            factFraction = personalInfo.factFraction,
            factHouse = personalInfo.factHouse,
            factStreet = personalInfo.factStreet,
            regFlat = personalInfo.regFlat,
            regFraction = personalInfo.regFraction,
            regHouse = personalInfo.regHouse,
            regStreet = personalInfo.regStreet
        )
    }

    override fun saveGeneralInfo(id: Long, generalInfoData: GeneralInfoData) {
        val user = db.users.values.firstOrNull { it -> it.id == id } ?:
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

    override fun getGeneralInfo(id: Long) : GetGeneralInfoResponseData {
        val user = db.users.values.firstOrNull { it -> it.id == id } ?:
        throw UserDoesNotExistsException("User does not exist")

        //check if user not filled general info form before
        if(user.cityId == (-1).toLong() && user.schoolId == (-1).toLong()) {
            return GetGeneralInfoResponseData()
        } else {
            val cityDto = db.cities[user.cityId] ?: throw BadRequestException("City Does Not Exist")
            val schoolDto = db.cities[user.cityId]!!.schools.firstOrNull { it.id == user.schoolId } ?: throw BadRequestException("School Does Not Exist")

            val responseData = GetGeneralInfoResponseData(
                firstName = user.firstName,
                middleName = user.middleName,
                lastName = user.lastName,
                birthDate = toDate(user.birthDate),
                city = CityData(
                    id = cityDto.id,
                    nameRu = cityDto.name,
                    nameEn = cityDto.name,
                    nameKk = cityDto.name
                ),
                accessType = AccessType.EDIT
            )

            if(schoolDto.schoolStatus == SchoolStatus.NONACTIVE) {
                responseData.customSchool = schoolDto.name
            } else {
                responseData.school = SchoolData(
                    id = schoolDto.id,
                    nameKk = schoolDto.name,
                    nameEn = schoolDto.name,
                    nameRu = schoolDto.name,
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