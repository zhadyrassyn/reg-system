package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.model.Document
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.server.domain.*
import kz.edu.sdu.regsystem.server.domain.enums.ExistType
import kz.edu.sdu.regsystem.server.exception.BadRequestException
import kz.edu.sdu.regsystem.server.repositoy.*
import kz.edu.sdu.regsystem.server.services.FileService
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat
import java.util.*

@Service
class StudentRegisterImpl(
    val infoRepository: InfoRepository,
    val personalInfoRepository: PersonalInfoRepository,
    val fileService: FileService,
    val documentRepository: DocumentRepository,
    val educationInfoRepository: EducationInfoRepository,
    val medicalInfoRepository: MedicalInfoRepository
) : StudentRegister {
    override fun savePersonalInfo(personalInfo: SavePersonalInfoRequest, id: Long) {
        //save customBirthPlace is exist
        val areaId = if (Objects.isNull(personalInfo.birthPlace) && !StringUtils.isEmpty(personalInfo.birthPlaceCustom)) {
            infoRepository.saveArea(
                Area(
                    id = -1,
                    nameRu = personalInfo.birthPlaceCustom!!,
                    nameKk = personalInfo.birthPlaceCustom!!,
                    nameEn = personalInfo.birthPlaceCustom!!,
                    type = ExistType.CUSTOM
                )
            )
        } else {
            personalInfo.birthPlace!!
        }

        val personalInfoDb: PersonalInfo? = personalInfoRepository.fetchPersonalInfo(id)
        if (Objects.isNull(personalInfoDb)) {
            personalInfoRepository.save(personalInfo, areaId, id)
        } else {
            personalInfoRepository.update(personalInfo, areaId, id, personalInfoDb!!.id)
        }
    }

    override fun getPersonalInfo(id: Long): GetPersonalInfoResponse? {
        val document = documentRepository.get(id)
        val personalInfo = personalInfoRepository.fetchPersonalInfo(id)
//        val personalInfo = personalInfoRepository.fetchPersonalInfoDocument(id)

        if (personalInfo == null && document == null) {
            return GetPersonalInfoResponse()
        }

        if(personalInfo != null) {
            val area = infoRepository.fetchArea(personalInfo.birthPlaceId)

            // if birth place is custom value
            val birthPlaceCustom: String? =
                if (area!!.type == ExistType.CUSTOM)
                    area.nameEn
                else
                    null

            return GetPersonalInfoResponse(
                id = id,
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
                birthPlace = area.id,
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
                ud_front = document?.ud_front,
                ud_back = document?.ud_back,
                photo3x4 = document?.photo3x4,

                comment = personalInfo.comment,
                status = personalInfo.status.name
            )
        } else {
            val response = GetPersonalInfoResponse()
            response.ud_front = document?.ud_front
            response.ud_back = document?.ud_back
            response.photo3x4 = document?.photo3x4
            return response
        }

    }

    fun toDate(birthDate: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(birthDate)
    }

    override fun savePersonalInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        val savedFileName = fileService.store(file)

        val documentDto = documentRepository.get(id)

        if (documentDto == null) {
            documentRepository.save(userId = id, fileName = savedFileName, documentType = documentType)
        } else {
            documentRepository.update(documentId = documentDto.id, fileName = savedFileName, documentType = documentType)
        }

        return Document(name = savedFileName)
    }

    override fun saveEducationInfo(educationInfo: SaveEducationInfoRequestData, id: Long) {
        val cityId = if (Objects.isNull(educationInfo.city) && !StringUtils.isEmpty(educationInfo.another_cityVillage)) {
            infoRepository.saveCity(
                City(
                    id = 1,
                    nameRu = educationInfo.another_cityVillage!!,
                    nameKk = educationInfo.another_cityVillage!!,
                    nameEn = educationInfo.another_cityVillage!!,
                    areaId = educationInfo.educationArea!!,
                    type = ExistType.CUSTOM
                )
            )
        } else
            educationInfo.city!!

        val schoolId = if (Objects.isNull(educationInfo.school) && !StringUtils.isEmpty(educationInfo.customSchool)) {
            infoRepository.saveSchool(
                School(
                    id = 1,
                    nameRu = educationInfo.customSchool!!,
                    nameKk = educationInfo.customSchool!!,
                    nameEn = educationInfo.customSchool!!,
                    cityId = cityId,
                    type = ExistType.CUSTOM
                )
            )
        } else
            educationInfo.school!!

        val educationInfoDto = educationInfoRepository.get(id)
        val save = EducationInfo(
            id = if (educationInfoDto == null) -1 else educationInfoDto.id,
            areaId = educationInfo.educationArea!!,
            cityId = cityId,
            schoolId = schoolId,
            schoolFinish = educationInfo.school_finish,
            entAmount = educationInfo.ent_amount.toInt(),
            entCertificateNumber = educationInfo.ent_certificate_number,
            ikt = educationInfo.ikt,
            facultyId = educationInfo.faculty,
            specialtyId = educationInfo.speciality,
            userId = id
        )
        if (educationInfoDto == null) {
            educationInfoRepository.save(save)
        } else {
            educationInfoRepository.update(save)
        }

    }

    override fun getEducationInfo(id: Long): GetEducationInfoResponseData {
        val document = documentRepository.get(id)
        val educationInfo = educationInfoRepository.get(id)

        if (document == null && educationInfo == null) {
            return GetEducationInfoResponseData()
        } else if(educationInfo != null) {
            val area = infoRepository.fetchArea(educationInfo.areaId)
                ?: throw BadRequestException("Cannot find area with id ${educationInfo.areaId}")

            val city = infoRepository.fetchCity(educationInfo.cityId)
                ?: throw BadRequestException("Cannot find city with id ${educationInfo.cityId}")

            val school = infoRepository.fetchSchool(educationInfo.schoolId)
                ?: throw BadRequestException("Cannot find school with id ${educationInfo.schoolId}")

            val faculty = infoRepository.fetchFaculty(educationInfo.facultyId)
                ?: throw BadRequestException("Cannot find faculty with id ${educationInfo.facultyId}")

            val specialty = infoRepository.fetchSpecialty(educationInfo.specialtyId)
                ?: throw BadRequestException("Cannot find specialty with id ${educationInfo.specialtyId}")

            return GetEducationInfoResponseData(
                id = educationInfo.id,
                educationArea = AreaData(
                    id = area.id,
                    nameRu = area.nameRu,
                    nameKk = area.nameKk,
                    nameEn = area.nameEn
                ),
                city = if (city.type !== ExistType.CUSTOM)
                    CityData(
                        id = city.id,
                        nameEn = city.nameEn,
                        nameRu = city.nameRu,
                        nameKk = city.nameKk
                    )
                else
                    null,
                another_cityVillage = if (city.type == ExistType.CUSTOM) city.nameEn else null,
                school = if (school.type != ExistType.CUSTOM)
                    SchoolData(
                        id = school.id,
                        nameEn = school.nameEn,
                        nameKk = school.nameKk,
                        nameRu = school.nameRu
                    )
                else
                    null,
                customSchool = if (school.type == ExistType.CUSTOM) school.nameEn else null,
                ent_amount = educationInfo.entAmount.toLong(),
                ent_certificate_number = educationInfo.entCertificateNumber,
                ikt = educationInfo.ikt,
                faculty = GetFacultiesResponseData(
                    id = faculty.id,
                    nameRu = faculty.nameRu,
                    nameKk = faculty.nameKk,
                    nameEn = faculty.nameEn
                ),
                speciality = GetSpecialtyResponseData(
                    id = specialty.id,
                    nameRu = specialty.nameRu,
                    nameEn = specialty.nameEn,
                    nameKk = specialty.nameKk
                ),
                school_finish = toDate(educationInfo.schoolFinish),
                schoolDiploma = document?.school_diploma,
                entCertificate = document?.ent_certificate,
                comment = educationInfo.comment,
                status = educationInfo.status.name
            )

        } else {
            val response = GetEducationInfoResponseData()
            response.entCertificate = document?.ent_certificate
            response.schoolDiploma = document?.school_diploma
            return response
        }

    }

    override fun saveEducationInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        val savedFileName = fileService.store(file)

        val documentDto = documentRepository.get(id)

        if (documentDto == null) {
            documentRepository.save(userId = id, fileName = savedFileName, documentType = documentType)
        } else {
            documentRepository.update(documentId = documentDto.id, fileName = savedFileName, documentType = documentType)
        }

        return Document(name = savedFileName)
    }

    override fun saveMedicalDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        val savedFileName = fileService.store(file)

        val documentDto = documentRepository.get(id)

        if (documentDto == null) {
            documentRepository.save(userId = id, fileName = savedFileName, documentType = documentType)
        } else {
            documentRepository.update(documentId = documentDto.id, fileName = savedFileName, documentType = documentType)
        }

        return Document(name = savedFileName)
    }

    override fun getMedicalInfo(id: Long): GetMedicalInfoResponseData {
        val document = documentRepository.get(id)

        val medicalInfo = medicalInfoRepository.fetchMedicalInfoDocument(id)
        if(medicalInfo == null && document == null) {
            return GetMedicalInfoResponseData()
        } else if(medicalInfo != null) {
            return GetMedicalInfoResponseData(
                comment = medicalInfo.comment,
                status = medicalInfo.status.name,
                form63 = medicalInfo.form63,
                form86 = medicalInfo.form86,
                flurography = medicalInfo.flurography
            )
        } else {
            val response = GetMedicalInfoResponseData()
            response.form86 = document?.form86
            response.form63 = document?.form63
            response.flurography = document?.flurography
            return response
        }
    }

}