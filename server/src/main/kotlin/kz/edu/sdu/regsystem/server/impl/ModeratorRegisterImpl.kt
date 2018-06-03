package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import kz.edu.sdu.regsystem.server.exception.BadRequestException
import kz.edu.sdu.regsystem.server.repositoy.*
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
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

    override fun getStudentsActive(): List<GetStudentsResponse> {
        return usersRepository.fetchStudents()
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

    override fun fetchStudentsXls(): ResponseEntity<ByteArray>? {
        val headers = HttpHeaders()
        headers.add("content-disposition", "attachment; filename=\"students.xls")
        return ResponseEntity.ok().headers(headers).body(getStudentsXls())
    }

    private fun getStudentsXls(): ByteArray{
        val students = getStudentsActive()

        try {
            HSSFWorkbook().use { book ->
                val baos = ByteArrayOutputStream()
                val sheet = book.createSheet("Студенты")

                var rowNumber = 0

                createHeaderCells(
                    sheet.createRow(rowNumber),
                    createHeaderStyle(book),
                    "Фамилия",
                    "Имя",
                    "Отчество",
                    "E-mail",
                    "Пол",
                    "Дата рождения",
                    "Дата выдачи УД",
                    "Место выдачи УД",
                    "ИИН",
                    "Номер УД",
                    "Сотовый телефон",
                    "Домашний телефон",
                    "Национальность",
                    "Место родения",
                    "Группа крови",
                    "Граждантсво",
                    "Фактческий улица",
                    "Фактческий дом",
                    "Фактческий дробь",
                    "Фактческий квартира",
                    "Регистрационная улица",
                    "Регистрационная дом",
                    "Регистрационная дробь",
                    "Регистрационная квартира",
                    "Область обучения",
                    "Город",
                    "Школа",
                    "ЕНТ баллы(без профиля)",
                    "Номер сертификата ЕНТ",
                    "ИКТ",
                    "Дата окончания школы",
                    "Желаемый фалультет",
                    "Желаемая специализация",
                    "Статус"

                )

                for (student in students) {
                    val personalInfo = fetchPersonalInfo(student.id)
                    val educationInfo = fetchEducationInfo(student.id)

                    rowNumber++
                    createCell(
                        sheet.createRow(rowNumber),
                        student.lastName,
                        student.firstName,
                        student.middleName ?: "",
                        student.email,
                        student.gender,
                        personalInfo.birthDate ?: "",
                        personalInfo.givenDate ?: "",
                        personalInfo.givenPlace ?: "",
                        personalInfo.iin ?: "",
                        personalInfo.ud_number ?: "",
                        personalInfo.mobilePhone ?: "",
                        personalInfo.telPhone ?: "",
                        personalInfo.nationality ?: "",
                        personalInfo.birthPlace?.nameRu ?: "",
                        personalInfo.blood_group ?: "",
                        personalInfo.citizenship ?: "",
                        personalInfo.factStreet ?: "",
                        personalInfo.factHouse ?: "",
                        personalInfo.factFraction ?: "",
                        personalInfo.factFlat ?: "",
                        personalInfo.regStreet ?: "",
                        personalInfo.regHouse ?: "",
                        personalInfo.regFraction ?: "",
                        personalInfo.regFlact ?: "",

                        educationInfo.educationArea?.nameRu ?: "",
                        educationInfo.city?.nameRu ?: "",
                        educationInfo.school?.nameRu ?: "",
                        educationInfo.ent_amount ?: "",
                        educationInfo.ent_certificate_number ?: "",
                        educationInfo.ikt ?: "",
                        educationInfo.school_finish ?: "",
                        educationInfo.faculty?.nameRu ?: "",
                        educationInfo.speciality?.nameRu ?: "",

                        student.generalStatus

                    )
                }

                for (i in 0..40) {
                    try {
                        sheet.autoSizeColumn(i)
                    } catch (ignored: NullPointerException) {
                    }

                }

                book.write(baos)
                book.close()
                return baos.toByteArray()
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    private fun createCell(row: Row, vararg names: String) {
        for (i in names.indices) {
            row.createCell(i, Cell.CELL_TYPE_STRING).setCellValue(names[i])
        }
    }

    private fun createHeaderStyle(book: Workbook): CellStyle {
        val style = book.createCellStyle()
        val font = book.createFont()
        font.setBoldweight(Font.BOLDWEIGHT_BOLD)
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.SKY_BLUE.getIndex()
        style.setFillPattern(CellStyle.SOLID_FOREGROUND)
        style.setAlignment(CellStyle.ALIGN_CENTER)
        return style
    }

    private fun createHeaderCells(row: Row, headerStyle: CellStyle, vararg names: String) {
        for (i in names.indices) {
            val header = row.createCell(i, Cell.CELL_TYPE_STRING)
            header.setCellValue(names[i])
            header.cellStyle = headerStyle
        }
    }
}