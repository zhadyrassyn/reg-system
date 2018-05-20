package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.SaveEducationInfoRequestData
import kz.edu.sdu.regsystem.controller.model.SavePersonalInfoRequest
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.server.domain.*
import kz.edu.sdu.regsystem.server.domain.enums.*
import kz.edu.sdu.regsystem.server.props.StorageProperties
import kz.edu.sdu.regsystem.server.repositoy.*
import kz.edu.sdu.regsystem.server.services.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert.*
import org.testng.annotations.Test
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*


@SpringBootTest
class StudentRegisterImplTest : AbstractTestNGSpringContextTests(){

    @Autowired
    lateinit var studentRegisterImpl: StudentRegisterImpl

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
    lateinit var fileService: FileService

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    lateinit var user: User

    lateinit var area: Area

    lateinit var a: SavePersonalInfoRequest

    var properties: StorageProperties = StorageProperties()

    private lateinit var rootLocation: Path

    init {
        this.rootLocation = Paths.get(properties.location)
    }

    private fun clearDb() {
        jdbcTemplate.execute("DELETE FROM DOCUMENT")
        jdbcTemplate.execute("DELETE FROM PersonalInfo")
        jdbcTemplate.execute("DELETE FROM EducationInfo")
        jdbcTemplate.execute("DELETE FROM MedicalInfo")
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

        a = SavePersonalInfoRequest(
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
    }

    @Test
    fun testSavePersonalInfoNotExistWithCustomBirthPlace() {
        clearDb()

        //
        //
        studentRegisterImpl.savePersonalInfo(a, user.id)
        //
        //

        val b = personalInfoRepository.fetchPersonalInfo(user.id)
        testSavePersonalInfoCheck(a, b)
        //check birth place value to a.birthPlaceCustom"
        val birthPlace = infoRepository.fetchArea(b!!.birthPlaceId)
        assertNotNull(birthPlace)
        assertEquals(birthPlace!!.nameEn, a.birthPlaceCustom)
        assertEquals(birthPlace.type, ExistType.CUSTOM)
    }

    fun testSavePersonalInfoCheck(left: SavePersonalInfoRequest, right: PersonalInfo?) {
        assertNotNull(right!!)
        assertEquals(right.firstName, left.firstName)
        assertEquals(right.middleName, left.middleName)
        assertEquals(right.lastName, left.lastName)
        assertEquals(right.gender, left.gender)
        assertEquals(right.birthDate, left.birthDate)
        assertEquals(right.givenDate, left.givenDate)
        assertEquals(right.givenPlace, left.givenPlace)
        assertEquals(right.iin, left.iin)
        assertEquals(right.ud_number, left.ud_number)
        assertEquals(right.mobilePhone, left.mobilePhone)
        assertEquals(right.telPhone, left.telPhone)
        assertEquals(right.nationality, left.nationality)
        assertNotNull(right.birthPlaceId)
        assertEquals(right.blood_group, left.blood_group)
        assertEquals(right.citizenship, left.citizenship)

        assertEquals(right.factFlat, left.factFlat)
        assertEquals(right.factFraction, left.factFraction)
        assertEquals(right.factHouse, left.factHouse)
        assertEquals(right.factStreet, left.factStreet)

        assertEquals(right.regFlat, left.regFlat)
        assertEquals(right.regFraction, left.regFraction)
        assertEquals(right.regHouse, left.regHouse)
        assertEquals(right.regStreet, left.regStreet)
    }

    @Test
    fun testSavePersonalInfoNotExistWithSystemBirthPlace() {
        clearDb()

        a.birthPlace = area.id
        a.birthPlaceCustom = "Pavlodar"

        //
        //
        studentRegisterImpl.savePersonalInfo(a, user.id)
        //
        //

        val b = personalInfoRepository.fetchPersonalInfo(user.id)

        testSavePersonalInfoCheck(a, b)

        assertEquals(b!!.birthPlaceId, a.birthPlace!!)
    }

    @Test
    fun testUpdatePersonalInfo() {
        clearDb()

        personalInfoRepository.save(personalInfo = a, areaId = area.id, userId = user.id)

         a = SavePersonalInfoRequest(
            firstName = "a",
            middleName = "b",
            lastName = "c",
            gender = GenderType.ANOTHER.name,
            birthDate = fromStrToDate("1997-07-15"),
            givenDate = fromStrToDate("2000-08-05"),
            givenPlace = "RK |||",
            iin = "970211555588",
            ud_number = "123123124",
            mobilePhone = "87021112234",
            telPhone = "77011111111",
            nationality = "kazakh1",

            birthPlace = area.id,
            birthPlaceCustom = "Pavlodar",

            blood_group = "first-",
            citizenship = "Russia",

            factFlat = "33",
            factFraction = "33",
            factHouse = "20",
            factStreet = "Tahibayeva1",

            regFlat = "11",
            regFraction = "22",
            regHouse = "44",
            regStreet = "Auezova"
        )

        //
        //
        studentRegisterImpl.savePersonalInfo(a, user.id)
        //
        //

        val b = personalInfoRepository.fetchPersonalInfo(user.id)

        testSavePersonalInfoCheck(a, b)

        assertEquals(b!!.birthPlaceId, a.birthPlace)
    }

    fun fromStrToDate(date: String) : Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(date)
    }

    private fun toDate(birthDate: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(birthDate)
    }

    @Test
    fun testGetPersonalInfoNotExist() {
        clearDb()

        //
        //
        val response = studentRegisterImpl.getPersonalInfo(user.id)
        //
        //

        assertNotNull(response)
        assertEquals(response?.ud_front, "")
        assertEquals(response?.ud_back, "")
        assertEquals(response?.photo3x4, "")
    }

    @Test
    fun testGetPersonalInfo() {
        clearDb()
        val document = Document(
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

        personalInfoRepository.save(personalInfo = a, areaId = area.id, userId = user.id)

        //
        //
        val response = studentRegisterImpl.getPersonalInfo(user.id)
        //
        //

        assertNotNull(response)
        assertEquals(response!!.firstName, a.firstName)
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
        assertEquals(response.birthPlace, area.id)
        assertNull(response.birthPlaceCustom)
        assertEquals(response.blood_group, a.blood_group)
        assertEquals(response.citizenship, a.citizenship)

        assertEquals(response.factFlat, a.factFlat)
        assertEquals(response.factFraction, a.factFraction)
        assertEquals(response.factHouse, a.factHouse)
        assertEquals(response.factStreet, a.factStreet)

        assertEquals(response.regFlat, a.regFlat)
        assertEquals(response.regFraction, a.regFraction)
        assertEquals(response.regHouse, a.regHouse)
        assertEquals(response.regStreet, a.regStreet)

        assertEquals(response.ud_back, document.ud_back)
        assertEquals(response.ud_front, document.ud_front)
        assertEquals(response.photo3x4, document.photo3x4)

        assertEquals(response.status, ConclusionStatus.WAITING_FOR_RESPONSE.name)
    }

    @Test
    fun testSavePersonalInfoDocument() {
        clearDb()

        val fileName = "testimg.png"
        val source = File("${System.getProperty("user.home")}/test/$fileName")

        fileService.deleteFile(fileName)
        val multipartFile = MockMultipartFile("testimg.png", "testimg.png", null, FileInputStream(source))


        //
        //
        val response = studentRegisterImpl.savePersonalInfoDocument(id = user.id,file = multipartFile, documentType = DocumentType.IDENTITY_CARD_FRONT)
        //
        //

        assertNotNull(response)
        assertNotNull(response.name)

        assertEquals(Files.readAllBytes(fileService.getFilePath(response.name)), Files.readAllBytes(source.toPath()))
    }

    @Test
    fun testSaveEducationInfoSystemValues() {
        clearDb()

        val city = City(
            nameRu = "Комсомольское",
            nameEn = "Komsomolskoe",
            nameKk = "Комсомольское",
            areaId = area.id
        )
        city.id = infoRepository.saveCity(city)

        val school = School(
            nameRu = "Шалкар НУ",
            nameEn = "Shalkar NU",
            nameKk = "Шалкар НУ",
            cityId = city.id
        )
        school.id = infoRepository.saveSchool(school)

        val f1 = Faculty(
            nameRu = "Бизнес школа СДУ",
            nameEn = "SDU Business school",
            nameKk = "СДУ Бизнес мектебі"
        )

        f1.id = infoRepository.saveFaculty(f1)

        val f1s1 = Specialty(
            nameKk = "Есеп және аудит",
            nameRu = "Учет и аудит",
            nameEn = "Accounting and auditing",
            faculty_id = f1.id
        )

        f1s1.id = infoRepository.saveSpecialty(f1s1)

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
            faculty = f1.id,
            speciality = f1s1.id,
            school_finish = fromStrToDate("2000-01-05")
        )

        //
        //
        studentRegisterImpl.saveEducationInfo(s, user.id)
        //
        //

        val educationInfoDto = educationInfoRepository.get(user.id)

        assertNotNull(educationInfoDto)
        assertEquals(educationInfoDto!!.areaId, s.educationArea)
        assertEquals(educationInfoDto.cityId, s.city)
        assertEquals(educationInfoDto.schoolId, s.school)
        assertEquals(educationInfoDto.entAmount, s.ent_amount.toInt())
        assertEquals(educationInfoDto.entCertificateNumber, s.ent_certificate_number)
        assertEquals(educationInfoDto.ikt, s.ikt)
        assertEquals(educationInfoDto.facultyId, s.faculty)
        assertEquals(educationInfoDto.specialtyId, s.speciality)
        assertEquals(educationInfoDto.schoolFinish, s.school_finish)
        assertEquals(educationInfoDto.comment, "")
        assertEquals(educationInfoDto.status, ConclusionStatus.WAITING_FOR_RESPONSE)
    }

    @Test
    fun testGetEducationInfo() {
    }

    @Test
    fun testSaveEducationInfoDocument() {
    }

    @Test
    fun testSaveMedicalDocument() {
    }

    @Test
    fun testGetMedicalInfo() {
    }

}