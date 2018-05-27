package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import kz.edu.sdu.regsystem.server.domain.enums.ExistType
import kz.edu.sdu.regsystem.server.exception.BadRequestException
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import kz.edu.sdu.regsystem.server.repositoy.PersonalInfoRepository
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class ModeratorRegisterImpl(
    val personalInfoRepository: PersonalInfoRepository,
    val infoRepository: InfoRepository
) : ModeratorRegister{
    override fun fetchPersonalInfo(id: Long): FetchPersonalInfoResponse {
        val personalInfo = personalInfoRepository.fetchPersonalInfoDocument(id) ?: return FetchPersonalInfoResponse()

        val area = infoRepository.fetchArea(personalInfo.birthPlaceId) ?: throw BadRequestException("Cannot find area with id ${personalInfo.birthPlaceId}")

        return FetchPersonalInfoResponse(
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

            birthPlace = AreaData(
                id = area.id,
                nameRu = area.nameRu,
                nameKk = area.nameKk,
                nameEn = area.nameEn
            ),

            blood_group = personalInfo.blood_group,
            citizenship = personalInfo.citizenship,
            factFlat = personalInfo.factFlat,
            factFraction = personalInfo.factFraction,
            factHouse = personalInfo.factHouse,
            factStreet = personalInfo.factStreet,
            regFlact = personalInfo.regFlat,
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

    override fun fetchEducationInfo(id: Long): FetchEducationInfoResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveEducationComment(id: Long, request: EditGeneralInfORequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchMedicalInfo(id: Long): FetchMedicalInfoResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveMedicalComment(id: Long, request: EditGeneralInfORequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStudentInfo(id: Long): GetStudentInfoResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editGeneralInfo(id: Long, request: EditGeneralInfORequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveCommentForDocuments(id: Long, request: SaveCommentForDocumentsRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeDocumentStatus(id: Long, documentId: Long, status: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTotalAmountOfStudents(text: String): FetchTotalAmountOfStudentsResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStudents(text: String, currentPage: Int, perPage: Int): List<GetStudentsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}