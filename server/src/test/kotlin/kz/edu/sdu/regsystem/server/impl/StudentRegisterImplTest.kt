package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.SavePersonalInfoRequest
import kz.edu.sdu.regsystem.server.domain.Area
import kz.edu.sdu.regsystem.server.domain.User
import kz.edu.sdu.regsystem.server.domain.enums.ExistType
import kz.edu.sdu.regsystem.server.domain.enums.GenderType
import kz.edu.sdu.regsystem.server.domain.enums.RoleType
import kz.edu.sdu.regsystem.server.domain.enums.UserStatus
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import kz.edu.sdu.regsystem.server.repositoy.PersonalInfoRepository
import kz.edu.sdu.regsystem.server.repositoy.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

import org.testng.Assert.*
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
    lateinit var jdbcTemplate: JdbcTemplate

    lateinit var user: User

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

    }

    @Test
    fun testSavePersonalInfoNotExistWithCustomBirthPlace() {
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


        //
        //
        studentRegisterImpl.savePersonalInfo(a, user.id)
        //
        //

        val b = personalInfoRepository.fetchPersonalInfo(user.id)

        assertNotNull(b)
        assertEquals(b!!.firstName, a.firstName)
        assertEquals(b.middleName, a.middleName)
        assertEquals(b.lastName, a.lastName)
        assertEquals(b.gender, a.gender)
        assertEquals(b.birthDate, a.birthDate)
        assertEquals(b.givenDate, a.givenDate)
        assertEquals(b.givenPlace, a.givenPlace)
        assertEquals(b.iin, a.iin)
        assertEquals(b.ud_number, a.ud_number)
        assertEquals(b.mobilePhone, a.mobilePhone)
        assertEquals(b.telPhone, a.telPhone)
        assertEquals(b.nationality, a.nationality)
        assertNotNull(b.birthPlaceId)
        assertEquals(b.blood_group, a.blood_group)
        assertEquals(b.citizenship, a.citizenship)

        assertEquals(b.factFlat, a.factFlat)
        assertEquals(b.factFraction, a.factFraction)
        assertEquals(b.factHouse, a.factHouse)
        assertEquals(b.factStreet, a.factStreet)

        assertEquals(b.regFlat, a.regFlat)
        assertEquals(b.regFraction, a.regFraction)
        assertEquals(b.regHouse, a.regHouse)
        assertEquals(b.regStreet, a.regStreet)

        //check birth place value to a.birthPlaceCustom"
        val birthPlace = infoRepository.fetchArea(b.birthPlaceId)
        assertNotNull(birthPlace)
        assertEquals(birthPlace!!.nameEn, a.birthPlaceCustom)
        assertEquals(birthPlace.type, ExistType.CUSTOM)
    }

    @Test
    fun testSavePersonalInfoNotExistWithSystemBirthPlace() {
        clearDb()

        val area = Area(
            nameEn = "abc1",
            nameKk = "abc2",
            nameRu = "abc3"
        )

        area.id = infoRepository.saveArea(area)

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

            birthPlace = area.id,
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


        //
        //
        studentRegisterImpl.savePersonalInfo(a, user.id)
        //
        //

        val b = personalInfoRepository.fetchPersonalInfo(user.id)

        assertNotNull(b)
        assertEquals(b!!.firstName, a.firstName)
        assertEquals(b.middleName, a.middleName)
        assertEquals(b.lastName, a.lastName)
        assertEquals(b.gender, a.gender)
        assertEquals(b.birthDate, a.birthDate)
        assertEquals(b.givenDate, a.givenDate)
        assertEquals(b.givenPlace, a.givenPlace)
        assertEquals(b.iin, a.iin)
        assertEquals(b.ud_number, a.ud_number)
        assertEquals(b.mobilePhone, a.mobilePhone)
        assertEquals(b.telPhone, a.telPhone)
        assertEquals(b.nationality, a.nationality)
        assertNotNull(b.birthPlaceId)
        assertEquals(b.blood_group, a.blood_group)
        assertEquals(b.citizenship, a.citizenship)

        assertEquals(b.factFlat, a.factFlat)
        assertEquals(b.factFraction, a.factFraction)
        assertEquals(b.factHouse, a.factHouse)
        assertEquals(b.factStreet, a.factStreet)

        assertEquals(b.regFlat, a.regFlat)
        assertEquals(b.regFraction, a.regFraction)
        assertEquals(b.regHouse, a.regHouse)
        assertEquals(b.regStreet, a.regStreet)

        assertEquals(b.birthPlaceId, a.birthPlace)
    }

    fun fromStrToDate(date: String) : Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(date)
    }

    @Test
    fun testGetPersonalInfo() {
    }

    @Test
    fun testSavePersonalInfoDocument() {
    }

    @Test
    fun testSaveEducationInfo() {
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