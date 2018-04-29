package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import org.springframework.web.multipart.MultipartFile

interface StudentRegister {
    fun savePersonalInfo(personalInfo: SavePersonalInfoRequest, id: Long)

    fun getPersonalInfo(id: Long): GetPersonalInfoResponse?

    fun savePersonalInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType) : Document

    fun saveEducationInfo(educationInfo: SaveEducationInfoRequestData, id: Long)
}