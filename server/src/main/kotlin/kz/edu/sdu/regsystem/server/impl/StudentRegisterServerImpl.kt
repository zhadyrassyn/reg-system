package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class StudentRegisterServerImpl : StudentRegister {
    override fun savePersonalInfo(personalInfo: SavePersonalInfoRequest, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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