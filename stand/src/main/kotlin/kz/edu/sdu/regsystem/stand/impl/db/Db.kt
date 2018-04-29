package kz.edu.sdu.regsystem.stand.impl.db

import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.stand.model.*
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@Component
class Db {
    val users = HashMap<Long, User>()
    val longCounter = AtomicLong()
    val verificationTokens = HashMap<Long, String>()
    val userRoles = HashMap<Long, RoleType>()
    val cities = HashMap<Long, City>()
    val areas = HashMap<Long, Area>()

    val faculties = HashMap<Long, UserFaculty>()

    init { //fill cities
        val a1 = Area(
            id = 10001,
            nameRu = "Акмолинская область",
            nameEn = "Akmolinskaya oblast",
            nameKk = "Акмолинская область"
        )

        val a1c1 = UserCity(
            id = longCounter.incrementAndGet(),
            nameRu = "Акколь",
            nameEn = "Akkol",
            nameKk = "Акколь"
        )

        val a1c1s1 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Акколь НУ",
            nameEn = "Akkol NU",
            nameKk = "Акколь НУ"
        )

        val a1c1s2 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Акколь БИЛ",
            nameEn = "Akkol BIL",
            nameKk = "Акколь БИЛ"
        )
        a1c1.schools[a1c1s1.id] = a1c1s1
        a1c1.schools[a1c1s2.id] = a1c1s2

        val a1c2 = UserCity(
            id = longCounter.incrementAndGet(),
            nameKk = "Коргалжын",
            nameRu = "Коргалжын",
            nameEn = "Korgalzhyn"
        )
        val a1c2s1 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Коргалжын НУ",
            nameKk = "Коргалжын НУ",
            nameEn = "Korgalzhyn NU"
        )
        val a1c2s2 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Коргалжын БИЛ",
            nameKk = "Коргалжын БИЛ",
            nameEn = "Korgalzhyn BIL"
        )
        a1c2.schools[a1c2s1.id] = a1c2s1
        a1c2.schools[a1c2s2.id] = a1c2s2

        a1.cities[a1c1.id] = a1c1
        a1.cities[a1c2.id] = a1c2

        val a2 = Area(
            id = 10002,
            nameRu = "Актюбинская область",
            nameEn = "Akubinskaya oblast",
            nameKk = "Актюбинская область"
        )

        val a2c1 = UserCity(
            id = longCounter.incrementAndGet(),
            nameRu = "Комсомольское",
            nameEn = "Komsomolskoe",
            nameKk = "Комсомольское"
        )

        val a2c1s1 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Комсомольское НУ",
            nameEn = "Komsomolskoe NU",
            nameKk = "Комсомольское НУ"
        )

        val a2c1s2 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Комсомольское БИЛ",
            nameEn = "Komsomolskoe BIL",
            nameKk = "Комсомольское БИЛ"
        )

        a2c1.schools[a2c1s1.id] = a2c1s1
        a2c1.schools[a2c1s2.id] = a2c1s2

        val a2c2 = UserCity(
            id = longCounter.incrementAndGet(),
            nameKk = "Шалкар",
            nameRu = "Шалкар",
            nameEn = "Shalkar"
        )

        val a2c2s1 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Шалкар НУ",
            nameEn = "Shalkar NU",
            nameKk = "Шалкар НУ"
        )

        val a2c2s2 = School(
            id = longCounter.incrementAndGet(),
            nameRu = "Шалкар БИЛ",
            nameEn = "Shalkar NU",
            nameKk = "Шалкар БИЛ"
        )
        a2c2.schools[a2c2s1.id] = a2c2s1
        a2c2.schools[a2c2s2.id] = a2c2s2


        a2.cities[a2c1.id] = a2c1
        a2.cities[a2c2.id] = a2c2
//
//        val a3 = Area(
//            id = 10003,
//            nameRu = "Алматинская область",
//            nameEn = "Almatinskaya oblast",
//            nameKk = "Алматинская область"
//        )
//        val a4 = Area(
//            id = 10004,
//            nameRu = "Атырауская область",
//            nameEn = "Atirauskaya oblast",
//            nameKk = "Атырауская область"
//        )
//        val a5 = Area(
//            id = 10005,
//            nameRu = "Восточно-Казахстанская область",
//            nameEn = "Vostochno-Kazakhstanskaya oblast",
//            nameKk = "Восточно-Казахстанская область"
//        )


        areas.put(a1.id, a1)
        areas.put(a2.id, a2)
//        areas.put(a3.id, a3)
//        areas.put(a4.id, a4)
//        areas.put(a5.id, a5)

        val documentEnums =
            arrayOf(DocumentType.DIPLOMA_CERTIFICATE,
                DocumentType.FLUOROGRAPHY, DocumentType.HEALTH_063, DocumentType.HEALTH_086,
                DocumentType.IDENTITY_CARD_BACK, DocumentType.IDENTITY_CARD_FRONT, DocumentType.PHOTO_3x4,
                DocumentType.UNT_CT_CERTIFICATE
            )

        val c1 = City(id = longCounter.incrementAndGet(), name = "Almaty")
        val c2 = City(id = longCounter.incrementAndGet(), name = "Astana")
        val c3 = City(id = longCounter.incrementAndGet(), name = "Qyzylorda")
        val c4 = City(id = longCounter.incrementAndGet(), name = "Shymkent")
        val c5 = City(id = longCounter.incrementAndGet(), name = "Aktobe")
        val c6 = City(id = longCounter.incrementAndGet(), name = "Taldykorgan")
        val c7 = City(id = longCounter.incrementAndGet(), name = "Pavlodar")

        cities[c1.id] = c1
        cities[c2.id] = c2
        cities[c3.id] = c3
        cities[c4.id] = c4
        cities[c5.id] = c5
        cities[c6.id] = c6
        cities[c7.id] = c7

        //fill schools
        cities.values.forEach {
            for (i in 0..10) {
                it.schools.add(
                    School(
                        id = longCounter.incrementAndGet(),
                        nameRu = "${it.name} school $i",
                        nameEn = "${it.name} school $i",
                        nameKk = "${it.name} school $i",
                        schoolStatus = SchoolStatus.SYSTEM))
            }
        }
        val u1 = User(
            id = 1000,
            email = "dandibobo537@gmail.com",
            password = "qwerty",
            userStatus = UserStatus.ACTIVE,
            firstName = "Daniyar",
            middleName = "Temirbekovich",
            lastName = "Zhadyrassyn",
            birthDate = toDate("1997/06/11"),
            cityId = c2.id,
            schoolId = c2.schools[0].id
        )

        documentEnums.forEach {
            u1.documents.put(it, Document(
                id = longCounter.incrementAndGet(),
                documentType = it,
                path = null
            ))
        }


        val u2 = User(
            id = longCounter.incrementAndGet(),
            email = "notActive@gmail.com",
            password = "qwerty",
            userStatus = UserStatus.NONACTIVE)

        val u3 = User(
            id = longCounter.incrementAndGet(),
            email = "moderator@test.com",
            password = "qwerty",
            userStatus = UserStatus.ACTIVE
        )

        val u4 = User(
            id = 2000,
            email = "test4@test.com",
            password = "qwerty",
            userStatus = UserStatus.ACTIVE
        )

        val stubUsers = ArrayList<User>()
        for (i in 0..20) {
            val user = User(
                id = longCounter.incrementAndGet(),
                email = "user$i@test.ru",
                password = "${i}000000",
                firstName = "User$i first name",
                middleName = "User$i middle name",
                lastName = "User$i last name",
                birthDate = toDate("1996/11/12"),
                cityId = c1.id,
                schoolId = c1.schools[i % 10].id,
                userStatus = UserStatus.ACTIVE
            )

            documentEnums.forEach {
                user.documents.put(it, Document(
                    id = longCounter.incrementAndGet(),
                    documentType = it,
                    path = null
                ))
            }

            users.put(user.id, user)
            userRoles.put(user.id, RoleType.USER)
        }

        users.put(u1.id, u1)
        users.put(u2.id, u2)
        users.put(u3.id, u3)
        users.put(u4.id, u4)

        verificationTokens.put(u1.id, "123")
        verificationTokens.put(u2.id, "678")

        userRoles.put(u1.id, RoleType.USER)
        userRoles.put(u2.id, RoleType.USER)
        userRoles.put(u3.id, RoleType.MODERATOR)
        userRoles.put(u4.id, RoleType.USER)

        val f1 = UserFaculty(
            id = longCounter.incrementAndGet(),
            nameRu = "Бизнес школа СДУ",
            nameEn = "SDU Business school",
            nameKk = "СДУ Бизнес мектебі"
        )

        val f1s1 = Speciality(
            id = longCounter.incrementAndGet(),
            nameKk = "Есеп және аудит",
            nameRu = "Учет и аудит",
            nameEn = "Accounting and auditing"
        )

        val f1s2 = Speciality(
            id = longCounter.incrementAndGet(),
            nameKk = "Маркетинг",
            nameRu = "Маркетинг",
            nameEn = "Marketing"
        )

        f1.specializations[f1s1.id] = f1s1
        f1.specializations[f1s2.id] = f1s2

        faculties[f1.id] = f1

        val f2 = UserFaculty(
            id = longCounter.incrementAndGet(),
            nameRu = "Факультет юриспруденции и социальнo-гуманитарных наук",
            nameEn = "Faculty of Law and Social sciences",
            nameKk = "Құқық және әлеуметтік-гуманитарлық ғылымдар факультеті"
        )

        val f2s1 = Speciality(
            id = longCounter.incrementAndGet(),
            nameKk = "Құқықтану",
            nameEn = "Jurisprudence",
            nameRu = "Юриспруденция"
        )
        f2.specializations[f2s1.id] = f2s1

        faculties[f2.id] = f2
    }

    private fun toDate(birthDate: String): Date {
        val pattern = "yyyy/MM/dd"
        val format = SimpleDateFormat(pattern)

        return format.parse(birthDate)
    }
}