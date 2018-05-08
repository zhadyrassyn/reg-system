package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.model.Document
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.*
import kz.edu.sdu.regsystem.stand.model.enums.AreaType
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import kz.edu.sdu.regsystem.stand.model.enums.UserCityStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import kz.edu.sdu.regsystem.stand.service.FileService
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat
import java.util.*

@Service
class StudentRegisterStandImpl(
    val db: Db,
    val fileService: FileService
) : StudentRegister {
    override fun getMedicalInfo(id: Long): GetMedicalInfoResponseData {
        val user = db.users.values.firstOrNull { id == it.id }
            ?: throw UserDoesNotExistsException("User does not exist")

        val medicalInfoDocuments = user.medicalInfoDocuments
        return GetMedicalInfoResponseData(
            form63 = medicalInfoDocuments.form63?.path?.fileName?.toString(),
            form86 = medicalInfoDocuments.form86?.path?.fileName?.toString(),
            flurography = medicalInfoDocuments.flurography?.path?.fileName?.toString()
        )
    }

    override fun saveMedicalDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        val user = db.users.values.firstOrNull { id == it.id }
            ?: throw UserDoesNotExistsException("User does not exist")

        var savedFile: kz.edu.sdu.regsystem.stand.model.Document? = null
        try {
            savedFile = fileService.store(file = file, documentType = documentType, id = id)
            if (documentType == DocumentType.HEALTH_086) {
                user.medicalInfoDocuments.form86 = savedFile
            } else if (documentType == DocumentType.HEALTH_063) {
                user.medicalInfoDocuments.form63 = savedFile
            } else if (documentType == DocumentType.FLUOROGRAPHY) {
                user.medicalInfoDocuments.flurography = savedFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Document(name = savedFile!!.path!!.fileName.toString())
    }

    override fun saveEducationInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        val user = db.users.values.firstOrNull { id == it.id }
            ?: throw UserDoesNotExistsException("User does not exist")

        var savedFile: kz.edu.sdu.regsystem.stand.model.Document? = null
        try {
            savedFile = fileService.store(file = file, documentType = documentType, id = id)
            if (documentType == DocumentType.DIPLOMA_CERTIFICATE) {
                user.educationInfoDocuments.schoolDiploma = savedFile
            } else if (documentType == DocumentType.UNT_CT_CERTIFICATE) {
                user.educationInfoDocuments.entCertificate = savedFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Document(name = savedFile!!.path!!.fileName.toString())
    }

    override fun savePersonalInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        val user = db.users.values.firstOrNull { id == it.id }
            ?: throw UserDoesNotExistsException("User does not exist")

        var savedFile: kz.edu.sdu.regsystem.stand.model.Document? = null
        try {
            savedFile = fileService.store(file = file, documentType = documentType, id = id)
            if (documentType == DocumentType.IDENTITY_CARD_FRONT) {
                user.personalInfoDocuments.ud_front = savedFile
            } else if (documentType == DocumentType.IDENTITY_CARD_BACK) {
                user.personalInfoDocuments.ud_back = savedFile
            } else if (documentType == DocumentType.PHOTO_3x4) {
                user.personalInfoDocuments.photo3x4 = savedFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Document(name = savedFile!!.path!!.fileName.toString())
    }

    override fun getPersonalInfo(id: Long): GetPersonalInfoResponse? {
        val user = db.users.values.firstOrNull { it -> it.id == id }
            ?: throw UserDoesNotExistsException("User does not exist")

        if (user.personalInfo == null) {
            return GetPersonalInfoResponse(
                ud_front = user.personalInfoDocuments.ud_front?.path?.fileName?.toString(),
                ud_back = user.personalInfoDocuments.ud_back?.path?.fileName?.toString(),
                photo3x4 = user.personalInfoDocuments.photo3x4?.path?.fileName?.toString()
            )
        }

        val personalInfo = user.personalInfo

        // if birth place is custom value
        val birthPlaceCustom: String? =
            if (personalInfo!!.birthPlace.status == AreaType.CUSTOM)
                personalInfo.birthPlace.nameRu
            else
                null
        return GetPersonalInfoResponse(
            id = user.id,
            firstName = personalInfo.firstName,
            middleName = personalInfo.middleName,
            lastName = personalInfo.lastName,
            gender = personalInfo.gender,
            birthDate = toDate(personalInfo.birthDate),
            givenDate = toDate(personalInfo.givenDate),
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
            ud_front = user.personalInfoDocuments.ud_front?.path?.fileName?.toString(),
            ud_back = user.personalInfoDocuments.ud_back?.path?.fileName?.toString(),
            photo3x4 = user.personalInfoDocuments.photo3x4?.path?.fileName?.toString(),

            comment = personalInfo.comment,
            status = personalInfo.personalInfoStatus.name
        )
    }

    override fun getEducationInfo(id: Long): GetEducationInfoResponseData {
        val user = db.users.values.firstOrNull { it -> it.id == id }
            ?: throw UserDoesNotExistsException("User does not exist")

        if (user.educationInfo == null) {
            return GetEducationInfoResponseData(
                schoolDiploma = user.educationInfoDocuments.schoolDiploma?.path?.fileName?.toString(),
                entCertificate = user.educationInfoDocuments.entCertificate?.path?.fileName?.toString()
            )
        }

        val educationInfo = user.educationInfo
        // if city is custom value
        val customCity: String? =
            if (educationInfo!!.city.status == UserCityStatus.CUSTOM)
                educationInfo.city.nameRu
            else
                null

        // if school is custom value
        val customSchool: String? =
            if (educationInfo.school.schoolStatus == SchoolStatus.CUSTOM) {
                educationInfo.school.nameRu
            } else
                null

        return GetEducationInfoResponseData(
            id = user.id,
            educationArea = AreaData(
                id = educationInfo.area.id,
                nameRu = educationInfo.area.nameRu,
                nameKk = educationInfo.area.nameKk,
                nameEn = educationInfo.area.nameEn
            ),
            city = if (educationInfo.city.status !== UserCityStatus.CUSTOM)
                CityData(
                    id = educationInfo.city.id,
                    nameEn = educationInfo.city.nameEn,
                    nameRu = educationInfo.city.nameRu,
                    nameKk = educationInfo.city.nameKk
                )
            else
                null,
            another_cityVillage = customCity,
            school = if (educationInfo.school.schoolStatus != SchoolStatus.CUSTOM)
                SchoolData(
                    id = educationInfo.school.id,
                    nameEn = educationInfo.school.nameEn,
                    nameKk = educationInfo.school.nameKk,
                    nameRu = educationInfo.school.nameRu
                )
            else
                null,
            customSchool = customSchool,
            ent_amount = educationInfo.ent_amount,
            ent_certificate_number = educationInfo.ent_certificate_number,
            ikt = educationInfo.ikt,
            faculty = GetFacultiesResponseData(
                id = educationInfo.faculty.id,
                nameRu = educationInfo.faculty.nameRu,
                nameKk = educationInfo.faculty.nameKk,
                nameEn = educationInfo.faculty.nameEn
            ),
            speciality = GetSpecialtyResponseData(
                id = educationInfo.speciality.id,
                nameRu = educationInfo.speciality.nameRu,
                nameEn = educationInfo.speciality.nameEn,
                nameKk = educationInfo.speciality.nameKk
            ),
            school_finish = toDate(educationInfo.school_finish),
            schoolDiploma = user.educationInfoDocuments.schoolDiploma?.path?.fileName?.toString(),
            entCertificate = user.educationInfoDocuments.entCertificate?.path?.fileName?.toString(),
            comment = educationInfo.comment,
            status = educationInfo.educationInfoStatus.name

        )
    }

    override fun saveEducationInfo(educationInfo: SaveEducationInfoRequestData, id: Long) {
        val user = db.users.values.firstOrNull { id == it.id }
            ?: throw UserDoesNotExistsException("User does not exist")

        val area: Area = db.areas[educationInfo.educationArea]
            ?: throw BadRequestException("Cannot find area with id ${educationInfo.educationArea}")

        var cityId = educationInfo.city
        if (Objects.isNull(educationInfo.city) && !StringUtils.isEmpty(educationInfo.another_cityVillage)) {
            try {
                val newCity = UserCity(
                    id = db.longCounter.incrementAndGet(),
                    nameKk = educationInfo.another_cityVillage!!,
                    nameRu = educationInfo.another_cityVillage!!,
                    nameEn = educationInfo.another_cityVillage!!,
                    status = UserCityStatus.CUSTOM
                )

                area.cities[newCity.id] = newCity
                cityId = newCity.id
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val city = area.cities[cityId] ?: throw BadRequestException("Cannot find city with id $cityId")
        var schoolId = educationInfo.school
        if (Objects.isNull(educationInfo.school) && !StringUtils.isEmpty(educationInfo.customSchool)) {
            try {
                val newSchool = School(
                    id = db.longCounter.incrementAndGet(),
                    nameRu = educationInfo.customSchool!!,
                    nameEn = educationInfo.customSchool!!,
                    nameKk = educationInfo.customSchool!!,
                    schoolStatus = SchoolStatus.CUSTOM
                )

                city.schools[newSchool.id] = newSchool
                schoolId = newSchool.id
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val school = city.schools[schoolId] ?: throw BadRequestException("Cannot find school with school id $schoolId")

        val faculty = db.faculties[educationInfo.faculty]
            ?: throw BadRequestException("Cannot find faculty with id ${educationInfo.faculty}")
        val speciality = faculty.specializations[educationInfo.speciality]
            ?: throw BadRequestException("Cannot find speciality with id ${educationInfo.speciality}")

        user.educationInfo = EducationInfo(
            id = db.longCounter.incrementAndGet(),
            area = area,
            city = city,
            school = school,
            ent_amount = educationInfo.ent_amount,
            ent_certificate_number = educationInfo.ent_certificate_number,
            ikt = educationInfo.ikt,
            faculty = faculty,
            speciality = speciality,
            school_finish = educationInfo.school_finish
        )
    }

    override fun savePersonalInfo(personalInfo: SavePersonalInfoRequest, id: Long) {
        println("Data $personalInfo")
        val user = db.users.values.firstOrNull { it -> it.id == id }
            ?: throw UserDoesNotExistsException("User does not exist")

        //save new are if birthPlaceCustom field is not null
        var areaId = personalInfo.birthPlace

        try {
            if (Objects.isNull(personalInfo.birthPlace) && !StringUtils.isEmpty(personalInfo.birthPlaceCustom)) {
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
        } catch (e: Exception) {
            e.printStackTrace()
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

    private fun toDate(birthDate: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(birthDate)
    }

}