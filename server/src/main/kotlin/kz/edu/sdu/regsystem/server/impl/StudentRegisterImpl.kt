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
            personalInfoRepository.update(personalInfo, areaId, id, personalInfo.id!!)
        }
    }

    override fun getPersonalInfo(id: Long): GetPersonalInfoResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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