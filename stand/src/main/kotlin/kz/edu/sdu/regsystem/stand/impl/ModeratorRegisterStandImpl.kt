package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.model.enums.EducationInfoStatus
import kz.edu.sdu.regsystem.controller.model.enums.PersonalInfoStatus
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.enums.CommentStatus
import kz.edu.sdu.regsystem.stand.model.enums.DocumentStatus
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


@Service
class ModeratorRegisterStandImpl(
    val db: Db
) : ModeratorRegister {

    override fun getStudentsActive(): List<GetStudentsResponse> {
        return db.users.values.filter { it.userStatus == UserStatus.ACTIVE && db.userRoles[it.id] == RoleType.USER }
            .map {
                if(it.personalInfo == null || it.educationInfo == null) {
                    GetStudentsResponse(id = it.id)
                } else {

                    val personalInfo = it.personalInfo
                    GetStudentsResponse(
                        id = it.id,
                        firstName = personalInfo!!.firstName,
                        middleName = personalInfo.middleName,
                        lastName = personalInfo.lastName,
                        iin = personalInfo.iin,
                        gender = "male",
                        generalStatus = "WAITING_FOR_RESPONSE"
                    )
                }


            }
    }

    override fun saveMedicalComment(id: Long, request: EditGeneralInfORequest) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        user.medicalInfoDocuments.comment = if(request.comment == null) "" else request.comment
        user.medicalInfoDocuments.status = CommentStatus.valueOf(request.status)
    }

    override fun fetchMedicalInfo(id: Long): FetchMedicalInfoResponse {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        val medicalInfo = user.medicalInfoDocuments

        return FetchMedicalInfoResponse(
            comment = medicalInfo.comment,
            status = medicalInfo.status.name,
            form86 = medicalInfo.form86?.path?.fileName?.toString(),
            form63 = medicalInfo.form63?.path?.fileName?.toString(),
            flurography = medicalInfo.flurography?.path?.fileName?.toString()
        )
    }

    override fun saveEducationComment(id: Long, request: EditGeneralInfORequest) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        user.educationInfo!!.comment = if (request.comment == null) "" else request.comment!!
        user.educationInfo!!.educationInfoStatus = EducationInfoStatus.valueOf(request.status)
    }

    override fun fetchEducationInfo(id: Long): FetchEducationInfoResponse {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        val educationInfo = user.educationInfo
            ?: return FetchEducationInfoResponse()

        return FetchEducationInfoResponse(
            educationArea = AreaData(
                id = educationInfo.area.id,
                nameKk = educationInfo.area.nameKk,
                nameRu = educationInfo.area.nameRu,
                nameEn = educationInfo.area.nameEn
            ),
            city = CityData(
                id = educationInfo.city.id,
                nameKk = educationInfo.city.nameKk,
                nameRu = educationInfo.city.nameRu,
                nameEn = educationInfo.city.nameEn
            ),
            school = SchoolData(
                id = educationInfo.school.id,
                nameKk = educationInfo.school.nameKk,
                nameRu = educationInfo.school.nameRu,
                nameEn = educationInfo.school.nameEn
            ),
            ent_amount = educationInfo.ent_amount.toString(),
            ent_certificate_number = educationInfo.ent_certificate_number,
            ikt = educationInfo.ikt,
            faculty = GetFacultiesResponseData(
                id = educationInfo.faculty.id,
                nameKk = educationInfo.faculty.nameKk,
                nameRu = educationInfo.faculty.nameRu,
                nameEn = educationInfo.faculty.nameEn
            ),
            speciality = GetSpecialtyResponseData(
                id = educationInfo.speciality.id,
                nameKk = educationInfo.speciality.nameKk,
                nameRu = educationInfo.speciality.nameRu,
                nameEn = educationInfo.speciality.nameEn
            ),
            school_finish = dateToStringForm(educationInfo.school_finish),
            schoolDiploma = user.educationInfoDocuments.schoolDiploma?.path?.fileName?.toString(),
            entCertificate = user.educationInfoDocuments.entCertificate?.path?.fileName?.toString(),
            comment = educationInfo.comment,
            status = educationInfo.educationInfoStatus.name
        )

    }

    override fun fetchPersonalInfo(id: Long): FetchPersonalInfoResponse {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        val personalInfo = user.personalInfo!!

        return FetchPersonalInfoResponse(
            id = id,
            firstName = personalInfo.firstName,
            middleName = personalInfo.middleName,
            lastName = personalInfo.lastName,
            gender = personalInfo.gender,
            birthDate = dateToStringForm(personalInfo.birthDate),
            givenDate = dateToStringForm(personalInfo.givenDate),
            iin = personalInfo.iin,
            ud_number = personalInfo.ud_number,
            mobilePhone = personalInfo.mobilePhone,
            telPhone = personalInfo.telPhone,
            nationality = personalInfo.nationality,
            birthPlace = AreaData(
                id = personalInfo.birthPlace.id,
                nameEn = personalInfo.birthPlace.nameEn,
                nameKk = personalInfo.birthPlace.nameKk,
                nameRu = personalInfo.birthPlace.nameRu
                ),
            blood_group = personalInfo.blood_group,
            citizenship = personalInfo.citizenship,
            factStreet = personalInfo.factStreet,
            factHouse = personalInfo.factHouse,
            factFraction = personalInfo.factFraction,
            factFlat = personalInfo.factFlat,
            regStreet = personalInfo.regStreet,
            regHouse = personalInfo.regHouse,
            regFraction = personalInfo.regFraction,
            regFlact = personalInfo.regFlat,
            givenPlace = personalInfo.givenPlace,

            ud_back = user.personalInfoDocuments.ud_back?.path?.fileName?.toString(),
            ud_front = user.personalInfoDocuments.ud_front?.path?.fileName?.toString(),
            photo3x4 = user.personalInfoDocuments.photo3x4?.path?.fileName?.toString(),

            comment = personalInfo.comment,
            status = personalInfo.personalInfoStatus.name
        )
    }

    override fun fetchTotalAmountOfStudents(text: String): FetchTotalAmountOfStudentsResponse {
        val response = FetchTotalAmountOfStudentsResponse()
        response.total = if (Objects.isNull(text)) {
            db.users.values
                .filter { db.userRoles[it.id] == RoleType.USER && it.userStatus == UserStatus.ACTIVE }.size
        } else {
            db.users.values
                .filter {
                    db.userRoles[it.id] == RoleType.USER
                        && it.userStatus == UserStatus.ACTIVE
                        && (
                        it.id.toString().contains(text, ignoreCase = true)
                            || it.firstName.contains(text, ignoreCase = true)
                            || it.middleName.contains(text, ignoreCase = true)
                            || it.lastName.contains(text, ignoreCase = true)
                            || db.cities[it.cityId]!!.name.contains(text, ignoreCase = true)
                            || db.cities[it.cityId]!!.schools.firstOrNull { school -> school.id == it.schoolId }!!.nameRu.contains(text, ignoreCase = true)
                            || dateToStringForm(it.birthDate).contains(text, ignoreCase = true)
                        )

                }.size
        }

        return response
    }

    override fun changeDocumentStatus(id: Long, documentId: Long, status: String) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        val document = user.documents.values.firstOrNull {
            it.id == documentId
        } ?: throw BadRequestException("Document with $documentId does not exist")

        document.documentStatus = DocumentStatus.valueOf(status)
    }

    override fun saveCommentForDocuments(id: Long, request: SaveCommentForDocumentsRequest) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        user.documentsComment = request.comment
    }

    override fun editGeneralInfo(id: Long, request: EditGeneralInfORequest) {
        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        user.personalInfo!!.comment = if (request.comment == null) "" else request.comment!!
        user.personalInfo!!.personalInfoStatus = PersonalInfoStatus.valueOf(request.status)
    }

    override fun getStudentInfo(id: Long): GetStudentInfoResponse {
        val response = GetStudentInfoResponse()

        val user = db.users.values.firstOrNull {
            it.id == id
        } ?: throw UserDoesNotExistsException("User with id $id does not exist")

        return GetStudentInfoResponse()
//        val personalInfo = user.personalInfo
//        if(personalInfo == null) {
//            return GetStudentInfoResponse()
//        } else {
//            val cityId: Long? = user.educationInfo?.city?.id
//            val city: String
//            val school: String
//            if(cityId == null) {
//                city = ""
//                school = ""
//            } else {
//                val area = db.areas[user.educationInfo!!.area.id]
//                val cityData = area!!.cities[cityId]
//                city = cityData!!.nameRu
//                school = cityData.schools[user.educationInfo!!.school.id]!!.nameRu
//            }
//            return GetStudentInfoResponse(
//                id = id,
//                firstName = personalInfo.firstName,
//                middleName = personalInfo.middleName,
//                lastName = personalInfo.lastName,
//                birthDate = dateToStringForm(personalInfo.birthDate),
//                city = city,
//                school = school
//            )
//        }
    }

    override fun getStudents(text: String, currentPage: Int, perPage: Int): List<GetStudentsResponse> {
        val filteredUsers = if (Objects.isNull(text)) {
            db.users.values
                .filter { db.userRoles[it.id] == RoleType.USER && it.userStatus == UserStatus.ACTIVE }
        } else {
            db.users.values
                .filter {
                    db.userRoles[it.id] == RoleType.USER
                        && it.userStatus == UserStatus.ACTIVE
                        && (
                        it.id.toString().contains(text, ignoreCase = true)
                            || it.firstName.contains(text, ignoreCase = true)
                            || it.middleName.contains(text, ignoreCase = true)
                            || it.lastName.contains(text, ignoreCase = true)
                            || db.cities[it.cityId]!!.name.contains(text, ignoreCase = true)
                            || db.cities[it.cityId]!!.schools.firstOrNull { school -> school.id == it.schoolId }!!.nameRu.contains(text, ignoreCase = true)
                            || dateToStringForm(it.birthDate).contains(text, ignoreCase = true)
                        )

                }
        }

        val total = filteredUsers.size
        val from = (currentPage - 1) * perPage
        val to = if (currentPage * perPage <= total) currentPage * perPage else total

        return filteredUsers
            .subList(from, to)
            .map {
                if(it.personalInfo == null || it.educationInfo == null) {
                    GetStudentsResponse(id = it.id)
                } else {
                    val city: String
                    val school: String

                    val educationArea = db.areas[it.educationInfo?.area?.id]
                    if(educationArea == null) {
                        city = ""
                        school = ""
                    } else {
                        val cityData = educationArea!!.cities[it.educationInfo!!.city.id]
                        city = cityData!!.nameEn
                        school = cityData.schools[it.educationInfo!!.school.id]!!.nameRu
                    }

                    val personalInfo = it.personalInfo
                    GetStudentsResponse(
                        id = it.id,
                        firstName = personalInfo!!.firstName,
                        middleName = personalInfo.middleName,
                        lastName = personalInfo.lastName,
                        iin = personalInfo.iin,
                        gender = personalInfo.gender,
                        generalStatus = "WAITING_FOR_RESPONSE"
                    )
                }


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
                    "Отчество"
                )

                for (student in students) {
                    rowNumber++
                    createCell(
                        sheet.createRow(rowNumber),
                        student.lastName,
                        student.firstName,
                        student.middleName ?: ""
                    )
                }

                for (i in 0..11) {
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

    private fun dateToStringForm(birthDate: Date): String {
        val df = SimpleDateFormat("dd/MM/yyyy")

        return df.format(birthDate)
    }

}