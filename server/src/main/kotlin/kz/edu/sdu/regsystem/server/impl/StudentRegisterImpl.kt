package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.server.domain.Area
import kz.edu.sdu.regsystem.server.domain.PersonalInfo
import kz.edu.sdu.regsystem.server.domain.enums.ExistType
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import kz.edu.sdu.regsystem.server.repositoy.PersonalInfoRepository
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat
import java.util.*

@Service
class StudentRegisterImpl(
    val infoRepository: InfoRepository,
    val personalInfoRepository: PersonalInfoRepository
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

        val personalInfoDb : PersonalInfo? = personalInfoRepository.fetchPersonalInfo(id)
        if(Objects.isNull(personalInfoDb)) {
            personalInfoRepository.save(personalInfo, areaId, id)
        } else {
            personalInfoRepository.update(personalInfo, areaId, id, personalInfoDb!!.id)
        }
    }

    override fun getPersonalInfo(id: Long): GetPersonalInfoResponse? {
        val personalInfo = personalInfoRepository.fetchPersonalInfoDocument(id)

        if(personalInfo == null) {
            return GetPersonalInfoResponse()
        }

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
            ud_front = personalInfo.ud_front,
            ud_back = personalInfo.ud_back,
            photo3x4 = personalInfo.photo3x4,

            comment = personalInfo.comment,
            status = personalInfo.status.name
        )
    }

    fun toDate(birthDate: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(birthDate)
    }

    override fun savePersonalInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveEducationInfo(educationInfo: SaveEducationInfoRequestData, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEducationInfo(id: Long): GetEducationInfoResponseData {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveEducationInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveMedicalDocument(id: Long, file: MultipartFile, documentType: DocumentType): Document {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMedicalInfo(id: Long): GetMedicalInfoResponseData {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}