package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.EditGeneralInfORequest
import kz.edu.sdu.regsystem.controller.model.SaveEducationInfoRequestData
import kz.edu.sdu.regsystem.controller.model.SavePersonalInfoRequest
import kz.edu.sdu.regsystem.server.domain.*
import kz.edu.sdu.regsystem.server.domain.enums.ConclusionStatus
import kz.edu.sdu.regsystem.server.domain.enums.GenderType
import kz.edu.sdu.regsystem.server.domain.enums.RoleType
import kz.edu.sdu.regsystem.server.domain.enums.UserStatus
import kz.edu.sdu.regsystem.server.repositoy.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

import org.testng.Assert.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
class ModeratorRegisterImplTest : AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var moderatorRegisterImpl: ModeratorRegisterImpl

    @Autowired
    lateinit var studentRegisterImpl: StudentRegisterImpl

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var personalInfoRepository: PersonalInfoRepository

    @Autowired
    lateinit var infoRepository: InfoRepository

    @Autowired
    lateinit var documentRepository: DocumentRepository

    @Autowired
    lateinit var educationInfoRepository: EducationInfoRepository

    @Autowired
    lateinit var medicalInfoRepository: MedicalInfoRepository

    lateinit var user: User
    lateinit var area: Area
    lateinit var city: City
    lateinit var school: School
    lateinit var faculty: Faculty
    lateinit var specialty: Specialty
    lateinit var document: Document

    private fun clearDb() {
        jdbcTemplate.execute("DELETE FROM DOCUMENT")
        jdbcTemplate.execute("DELETE FROM PersonalInfo")
        jdbcTemplate.execute("DELETE FROM EducationInfo")
        jdbcTemplate.execute("DELETE FROM MedicalInfo")
        jdbcTemplate.execute("DELETE FROM SCHOOL")
        jdbcTemplate.execute("DELETE FROM CITY")
        jdbcTemplate.execute("DELETE FROM AREA")
        jdbcTemplate.execute("DELETE FROM SPECIALTY")
        jdbcTemplate.execute("DELETE FROM FACULTY")
        jdbcTemplate.execute("DELETE FROM VERIFICATION_TOKEN")
        jdbcTemplate.execute("DELETE FROM USERS")

        //createUser
        user = User(
            email = "test@gmail.com",
            password = "123123",
            regDate = Date(),
            status = UserStatus.ACTIVE,
            role = RoleType.USER
        )
        user.id = usersRepository.save(user)

        area = Area(
            nameEn = "abc1",
            nameKk = "abc2",
            nameRu = "abc3"
        )

        area.id = infoRepository.saveArea(area)

        city = City(
            nameRu = "Комсомольское",
            nameEn = "Komsomolskoe",
            nameKk = "Комсомольское",
            areaId = area.id
        )
        city.id = infoRepository.saveCity(city)

        school = School(
            nameRu = "Шалкар НУ",
            nameEn = "Shalkar NU",
            nameKk = "Шалкар НУ",
            cityId = city.id
        )
        school.id = infoRepository.saveSchool(school)

        faculty = Faculty(
            nameRu = "Бизнес школа СДУ",
            nameEn = "SDU Business school",
            nameKk = "СДУ Бизнес мектебі"
        )

        faculty.id = infoRepository.saveFaculty(faculty)

        specialty = Specialty(
            nameKk = "Есеп және аудит",
            nameRu = "Учет и аудит",
            nameEn = "Accounting and auditing",
            faculty_id = faculty.id
        )

        specialty.id = infoRepository.saveSpecialty(specialty)

        document = Document(
            id = -1,
            ud_back = "UdBack",
            ud_front = "UdFront",
            photo3x4 = "photo3x4",
            school_diploma = "school_diploma",
            ent_certificate = "ent_certificate",
            form86 = "form86",
            form63 = "form63",
            flurography = "flurography",
            userId = user.id
        )
        document.id = documentRepository.save(document)
    }
    @Test
    fun testFetchPersonalInfoNotExist() {
        clearDb()

        //
        //
        val response = moderatorRegisterImpl.fetchPersonalInfo(user.id)
        //
        //

        assertNotNull(response)
        assertEquals(response.comment, "")
        assertEquals(response.status, ConclusionStatus.WAITING_FOR_RESPONSE.name)
        assertEquals(response.ud_front, "")
        assertEquals(response.ud_back, "")
        assertEquals(response.photo3x4, "")
    }

    @Test
    fun testFetchPersonalInfo() {
        clearDb()

        val a = SavePersonalInfoRequest(
            firstName = "Daniyar",
            middleName = null,
            lastName = "Qazbek",
            gender = GenderType.MALE.name,
            birthDate = fromStrToDate("1997-06-15"),
            givenDate = fromStrToDate("2000-01-05"),
            givenPlace = "RK ||",
            iin = "970211555589",
            ud_number = "123123123",
            mobilePhone = "87021112233",
            telPhone = null,
            nationality = "kazakh",

            birthPlace = null,
            birthPlaceCustom = "Pavlodar",

            blood_group = "first+",
            citizenship = "Kazakhstan",

            factFlat = "12",
            factFraction = "22",
            factHouse = "20",
            factStreet = "Tahibayeva",

            regFlat = null,
            regFraction = null,
            regHouse = "50",
            regStreet = "Abaya"
        )

        studentRegisterImpl.savePersonalInfo(a, user.id)

        //
        //
        val response = moderatorRegisterImpl.fetchPersonalInfo(user.id)
        //
        //

        assertNotNull(response)
        assertEquals(response.firstName, a.firstName)
        assertEquals(response.middleName, a.middleName)
        assertEquals(response.lastName, a.lastName)
        assertEquals(response.gender, a.gender)
        assertEquals(response.birthDate, toDate(a.birthDate))
        assertEquals(response.givenDate, toDate(a.givenDate))
        assertEquals(response.givenPlace, a.givenPlace)
        assertEquals(response.iin, a.iin)
        assertEquals(response.ud_number, a.ud_number)
        assertEquals(response.mobilePhone, a.mobilePhone)
        assertEquals(response.telPhone, a.telPhone)
        assertEquals(response.nationality, a.nationality)

        assertEquals(response.birthPlace!!.nameKk, a.birthPlaceCustom)
        assertEquals(response.birthPlace!!.nameEn, a.birthPlaceCustom)
        assertEquals(response.birthPlace!!.nameRu, a.birthPlaceCustom)

        assertEquals(response.blood_group, a.blood_group)
        assertEquals(response.citizenship, a.citizenship)

        assertEquals(response.factFlat, a.factFlat)
        assertEquals(response.factFraction, a.factFraction)
        assertEquals(response.factHouse, a.factHouse)
        assertEquals(response.factStreet, a.factStreet)

        assertEquals(response.regFlact, a.regFlat)
        assertEquals(response.regFraction, a.regFraction)
        assertEquals(response.regHouse, a.regHouse)
        assertEquals(response.regStreet, a.regStreet)

        assertEquals(response.ud_back, document.ud_back)
        assertEquals(response.ud_front, document.ud_front)
        assertEquals(response.photo3x4, document.photo3x4)

        assertEquals(response.comment, "")
        assertEquals(response.status, ConclusionStatus.WAITING_FOR_RESPONSE.name)
    }

    @Test
    fun testFetchEducationInfo() {
        clearDb()

        val s = SaveEducationInfoRequestData(
            id = -1,
            educationArea = area.id,
            city = city.id,
            another_cityVillage = null,
            school = school.id,
            customSchool = null,
            ent_amount = 112,
            ent_certificate_number = "123213",
            ikt = "2221",
            faculty = faculty.id,
            speciality = specialty.id,
            school_finish = fromStrToDate("2000-01-05")
        )

        studentRegisterImpl.saveEducationInfo(s, user.id)

        //
        //
        val response = moderatorRegisterImpl.fetchEducationInfo(user.id)
        //
        //

        assertNotNull(response)
        assertEquals(response.educationArea!!.id, s.educationArea)
        assertEquals(response.educationArea!!.nameKk, area.nameKk)
        assertEquals(response.educationArea!!.nameRu, area.nameRu)
        assertEquals(response.educationArea!!.nameEn, area.nameEn)

        assertEquals(response.city!!.id, s.city!!)
        assertEquals(response.city!!.nameKk, city.nameKk)
        assertEquals(response.city!!.nameRu, city.nameRu)
        assertEquals(response.city!!.nameEn, city.nameEn)

        assertEquals(response.school!!.id, s.school!!)
        assertEquals(response.school!!.nameKk, school.nameKk)
        assertEquals(response.school!!.nameRu, school.nameRu)
        assertEquals(response.school!!.nameEn, school.nameEn)

        assertEquals(response.ent_amount, s.ent_amount.toString())
        assertEquals(response.ent_certificate_number, s.ent_certificate_number)
        assertEquals(response.ikt, s.ikt)

        assertEquals(response.faculty!!.id, s.faculty)
        assertEquals(response.faculty!!.nameKk, faculty.nameKk)
        assertEquals(response.faculty!!.nameRu, faculty.nameRu)
        assertEquals(response.faculty!!.nameEn, faculty.nameEn)


        assertEquals(response.speciality!!.id, s.speciality)
        assertEquals(response.speciality!!.nameEn, specialty.nameEn)
        assertEquals(response.speciality!!.nameKk, specialty.nameKk)
        assertEquals(response.speciality!!.nameRu, specialty.nameRu)

        assertEquals(response.school_finish, toDate(s.school_finish))

        assertEquals(response.comment, "")
        assertEquals(response.status, ConclusionStatus.WAITING_FOR_RESPONSE.name)

        assertEquals(response.schoolDiploma, document.school_diploma)
        assertEquals(response.entCertificate, document.ent_certificate)
    }

    @Test
    fun testFetchMedicalInfo() {
        clearDb()

        val m = MedicalInfo(
            comment = "123",
            userId = user.id
        )

        m.id = medicalInfoRepository.save(m)

        //
        //
        val response = moderatorRegisterImpl.fetchMedicalInfo(user.id)
        //
        //

        assertNotNull(response)
        assertEquals(response.comment, m.comment)
        assertEquals(response.status, m.status.name)
        assertEquals(response.form86, document.form86)
        assertEquals(response.form63, document.form63)
        assertEquals(response.flurography, document.flurography)
    }

    @Test
    fun testSaveEducationComment() {
        clearDb()

        val s = SaveEducationInfoRequestData(
            id = -1,
            educationArea = area.id,
            city = city.id,
            another_cityVillage = null,
            school = school.id,
            customSchool = null,
            ent_amount = 112,
            ent_certificate_number = "123213",
            ikt = "2221",
            faculty = faculty.id,
            speciality = specialty.id,
            school_finish = fromStrToDate("2000-01-05")
        )

        studentRegisterImpl.saveEducationInfo(s, user.id)
        val saveRequest = EditGeneralInfORequest(comment = "asdfghjkl", status = "VALID")

        //
        //
        moderatorRegisterImpl.saveEducationComment(user.id, saveRequest)
        //
        //

        val dto = educationInfoRepository.get(user.id)
        assertNotNull(dto)
        assertEquals(dto?.comment, saveRequest.comment)
        assertEquals(dto?.status?.name, saveRequest.status)
    }

    @Test
    fun testSaveMedicalComment() {
        clearDb()

        val m = MedicalInfo(
            comment = "123",
            userId = user.id
        )

        m.id = medicalInfoRepository.save(m)

        val saveRequest = EditGeneralInfORequest(comment = "asdfghjkl", status = "INVALID")

        //
        //
        moderatorRegisterImpl.saveMedicalComment(user.id, saveRequest)
        //
        //

        val dto = medicalInfoRepository.fetchMedicalInfoDocument(user.id)
        assertNotNull(dto)
        assertEquals(dto?.comment, saveRequest.comment)
        assertEquals(dto?.status?.name, saveRequest.status)
    }

    @Test
    fun testEditGeneralInfo() {
        clearDb()

        val a = SavePersonalInfoRequest(
            firstName = "Daniyar",
            middleName = null,
            lastName = "Qazbek",
            gender = GenderType.MALE.name,
            birthDate = fromStrToDate("1997-06-15"),
            givenDate = fromStrToDate("2000-01-05"),
            givenPlace = "RK ||",
            iin = "970211555589",
            ud_number = "123123123",
            mobilePhone = "87021112233",
            telPhone = null,
            nationality = "kazakh",

            birthPlace = null,
            birthPlaceCustom = "Pavlodar",

            blood_group = "first+",
            citizenship = "Kazakhstan",

            factFlat = "12",
            factFraction = "22",
            factHouse = "20",
            factStreet = "Tahibayeva",

            regFlat = null,
            regFraction = null,
            regHouse = "50",
            regStreet = "Abaya"
        )

        studentRegisterImpl.savePersonalInfo(a, user.id)

        val saveRequest = EditGeneralInfORequest(comment = "asdfghjkl", status = "INVALID")

        //
        //
        moderatorRegisterImpl.editGeneralInfo(user.id, saveRequest)
        //
        //

        val dto = personalInfoRepository.fetchPersonalInfo(user.id)
        assertNotNull(dto)
        assertEquals(dto?.comment, saveRequest.comment)
        assertEquals(dto?.status?.name, saveRequest.status)

    }

    @Test
    fun testGetStudentInfo() {
    }

    @Test
    fun testSaveCommentForDocuments() {
    }

    @Test
    fun testChangeDocumentStatus() {
    }

    @Test
    fun testFetchTotalAmountOfStudentsActivAndFilled() {
        clearDb()

        val user2 = User(
            email = "test@gmail.com",
            password = "123123",
            regDate = Date(),
            status = UserStatus.ACTIVE,
            role = RoleType.USER
        )

        user2.id = usersRepository.save(user2)

        val a = SavePersonalInfoRequest(
            firstName = "Daniyar",
            middleName = null,
            lastName = "Qazbek",
            gender = GenderType.MALE.name,
            birthDate = fromStrToDate("1997-06-15"),
            givenDate = fromStrToDate("2000-01-05"),
            givenPlace = "RK ||",
            iin = "970211555589",
            ud_number = "123123123",
            mobilePhone = "87021112233",
            telPhone = null,
            nationality = "kazakh",

            birthPlace = null,
            birthPlaceCustom = "Pavlodar",

            blood_group = "first+",
            citizenship = "Kazakhstan",

            factFlat = "12",
            factFraction = "22",
            factHouse = "20",
            factStreet = "Tahibayeva",

            regFlat = null,
            regFraction = null,
            regHouse = "50",
            regStreet = "Abaya"
        )

        studentRegisterImpl.savePersonalInfo(a, user2.id)

        val m = MedicalInfo(
            comment = "123",
            userId = user2.id
        )

        m.id = medicalInfoRepository.save(m)

        val s = SaveEducationInfoRequestData(
            id = -1,
            educationArea = area.id,
            city = city.id,
            another_cityVillage = null,
            school = school.id,
            customSchool = null,
            ent_amount = 112,
            ent_certificate_number = "123213",
            ikt = "2221",
            faculty = faculty.id,
            speciality = specialty.id,
            school_finish = fromStrToDate("2000-01-05")
        )

        studentRegisterImpl.saveEducationInfo(s, user2.id)

        //
        //
        val response = moderatorRegisterImpl.fetchTotalAmountOfStudents("")
        //
        //

        assertNotNull(response)
        assertEquals(1, response.total)
    }

    @Test
    fun testGetStudents() {
        clearDb()

        val list = ArrayList<User>()
        val personalInfos = ArrayList<SavePersonalInfoRequest>()
        val medicalInfos = ArrayList<MedicalInfo>()
        val educationInfos = ArrayList<SaveEducationInfoRequestData>()
        for (i in 0..20) {
            val newUser = User(
                email = "test$i@mail.com",
                password = "123123",
                regDate = Date(),
                status = UserStatus.ACTIVE,
                role = RoleType.USER
            )
            list.add(newUser)

            newUser.id = usersRepository.save(newUser)

            val a = SavePersonalInfoRequest(
                firstName = "$i Daniyar",
                middleName = null,
                lastName = "$i Qazbek",
                gender = GenderType.MALE.name,
                birthDate = fromStrToDate("1997-06-15"),
                givenDate = fromStrToDate("2000-01-05"),
                givenPlace = "RK ||",
                iin = "970211555589",
                ud_number = "123123123",
                mobilePhone = "87021112233",
                telPhone = null,
                nationality = "kazakh",

                birthPlace = null,
                birthPlaceCustom = "Pavlodar",

                blood_group = "first+",
                citizenship = "Kazakhstan",

                factFlat = "12",
                factFraction = "22",
                factHouse = "20",
                factStreet = "Tahibayeva",

                regFlat = null,
                regFraction = null,
                regHouse = "50",
                regStreet = "Abaya"
            )

            studentRegisterImpl.savePersonalInfo(a, newUser.id)
            personalInfos.add(a)

            val m = MedicalInfo(
                comment = "123",
                userId = newUser.id
            )

            m.id = medicalInfoRepository.save(m)
            medicalInfos.add(m)

            val s = SaveEducationInfoRequestData(
                id = -1,
                educationArea = area.id,
                city = city.id,
                another_cityVillage = null,
                school = school.id,
                customSchool = null,
                ent_amount = 112,
                ent_certificate_number = "123213",
                ikt = "2221",
                faculty = faculty.id,
                speciality = specialty.id,
                school_finish = fromStrToDate("2000-01-05")
            )

            studentRegisterImpl.saveEducationInfo(s, newUser.id)
            educationInfos.add(s)
        }

        //
        //
        val response = moderatorRegisterImpl.getStudents(text = "da", currentPage = 1, perPage = 10)
        //
        //

        assertNotNull(response)
        assertEquals(10, response.size)

        for (i in 0..response.size-1) {
            assertEquals(list[i].id, response[i].id)
            assertEquals(list[i].email, response[i].email)
            assertEquals(personalInfos[i].firstName, response[i].firstName)
            assertEquals(personalInfos[i].middleName ?: "", response[i].middleName)
            assertEquals(personalInfos[i].lastName, response[i].lastName)
            assertEquals(personalInfos[i].iin, response[i].iin)
            assertEquals(list[i].email, response[i].email)
            assertEquals(personalInfos[i].gender, response[i].gender)
            assertEquals(ConclusionStatus.WAITING_FOR_RESPONSE.name, response[i].generalStatus)
        }

    }

    fun fromStrToDate(date: String) : Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(date)
    }

    private fun toDate(birthDate: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(birthDate)
    }

}