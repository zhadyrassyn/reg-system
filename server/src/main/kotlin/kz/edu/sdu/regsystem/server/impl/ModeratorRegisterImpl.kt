package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import kz.edu.sdu.regsystem.server.exception.BadRequestException
import kz.edu.sdu.regsystem.server.repositoy.*
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class ModeratorRegisterImpl(
    val personalInfoRepository: PersonalInfoRepository,
    val educationInfoRepository: EducationInfoRepository,
    val medicalInfoRepository: MedicalInfoRepository,
    val infoRepository: InfoRepository,
    val usersRepository: UsersRepository
) : ModeratorRegister {
    override fun fetchPersonalInfo(id: Long): FetchPersonalInfoResponse {
        val personalInfo = personalInfoRepository.fetchPersonalInfoDocument(id) ?: return FetchPersonalInfoResponse()

        val area = infoRepository.fetchArea(personalInfo.birthPlaceId)
            ?: throw BadRequestException("Cannot find area with id ${personalInfo.birthPlaceId}")

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
        val educationInfo = educationInfoRepository.fetchEducationInfoDocument(id)
            ?: return FetchEducationInfoResponse()

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

        return FetchEducationInfoResponse(
            id = educationInfo.id,
            educationArea = AreaData(
                id = area.id,
                nameRu = area.nameRu,
                nameKk = area.nameKk,
                nameEn = area.nameEn
            ),
            city = CityData(
                id = city.id,
                nameEn = city.nameEn,
                nameRu = city.nameRu,
                nameKk = city.nameKk),
            school = SchoolData(
                id = school.id,
                nameEn = school.nameEn,
                nameKk = school.nameKk,
                nameRu = school.nameRu
            ),
            ent_amount = educationInfo.entAmount.toString(),
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
            schoolDiploma = educationInfo.school_diploma,
            entCertificate = educationInfo.ent_certificate,
            comment = educationInfo.comment,
            status = educationInfo.status.name
        )
    }

    override fun saveEducationComment(id: Long, request: EditGeneralInfORequest) {
        val comment = request.comment ?: ""
        val status = ConclusionStatus.valueOf(request.status)

        educationInfoRepository.updateStatus(userId = id, comment = comment, status = status)
    }

    override fun fetchMedicalInfo(id: Long): FetchMedicalInfoResponse {
        val medicalInfo = medicalInfoRepository.fetchMedicalInfoDocument(id) ?: return FetchMedicalInfoResponse()

        return FetchMedicalInfoResponse(
            id = medicalInfo.id,
            comment = medicalInfo.comment,
            status = medicalInfo.status.name,
            form63 = medicalInfo.form63,
            form86 = medicalInfo.form86,
            flurography = medicalInfo.flurography
        )
    }

    override fun saveMedicalComment(id: Long, request: EditGeneralInfORequest) {
        val comment = request.comment ?: ""
        val status = ConclusionStatus.valueOf(request.status)

        medicalInfoRepository.updateStatus(userId = id, comment = comment, status = status)
    }

    override fun editGeneralInfo(id: Long, request: EditGeneralInfORequest) {
        val comment = request.comment ?: ""
        val status = ConclusionStatus.valueOf(request.status)

        personalInfoRepository.updateStatus(userId = id, comment = comment, status = status)
    }

    override fun getStudentInfo(id: Long): GetStudentInfoResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveCommentForDocuments(id: Long, request: SaveCommentForDocumentsRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeDocumentStatus(id: Long, documentId: Long, status: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTotalAmountOfStudents(text: String): FetchTotalAmountOfStudentsResponse {
        val total = usersRepository.fetchTotal(text.toLowerCase())
        return FetchTotalAmountOfStudentsResponse(total = total)
    }

    override fun getStudents(text: String, currentPage: Int, perPage: Int): List<GetStudentsResponse> {
        val offset = (currentPage - 1) * perPage
        return usersRepository.fetchUsers(text = text.toLowerCase(), offset = offset, perPage = perPage)
            .map {
                GetStudentsResponse(
                    id = it.id,
                    firstName = it.firstName,
                    middleName = it.middleName ?: "",
                    lastName = it.lastName,
                    iin = it.iin,
                    email = it.email,
                    gender = it.gender.name,
                    generalStatus =
                    if (it.pi_status == ConclusionStatus.VALID &&
                        it.ei_status == ConclusionStatus.VALID &&
                        it.mi_status == ConclusionStatus.VALID) ConclusionStatus.VALID.name
                    else
                        if (it.pi_status == ConclusionStatus.INVALID || it.ei_status == ConclusionStatus.INVALID ||
                            it.mi_status == ConclusionStatus.INVALID) ConclusionStatus.INVALID.name
                        else
                            ConclusionStatus.WAITING_FOR_RESPONSE.name
                )
            }

    }
}