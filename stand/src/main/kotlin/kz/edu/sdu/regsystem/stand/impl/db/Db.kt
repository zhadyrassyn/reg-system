package kz.edu.sdu.regsystem.stand.impl.db

import kz.edu.sdu.regsystem.stand.model.City
import kz.edu.sdu.regsystem.stand.model.Document
import kz.edu.sdu.regsystem.stand.model.School
import kz.edu.sdu.regsystem.stand.model.User
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Component
class Db {
    val users = HashMap<Long, User>()
    val longCounter = AtomicLong()
    val verificationTokens = HashMap<Long, String>()
    val userRoles = HashMap<Long, RoleType>()
    val cities = HashMap<Long, City>()

    init { //fill cities
        val c1 = City(id = longCounter.incrementAndGet(), name = "Almaty")
        val c2 = City(id = longCounter.incrementAndGet(), name = "Astana")
        val c3 = City(id = longCounter.incrementAndGet(), name ="Qyzylorda")
        val c4 = City(id = longCounter.incrementAndGet(), name ="Shymkent")
        val c5 = City(id = longCounter.incrementAndGet(), name ="Aktobe")
        val c6 = City(id = longCounter.incrementAndGet(), name ="Taldykorgan")
        val c7 = City(id = longCounter.incrementAndGet(), name ="Pavlodar")

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
                        name = "${it.name} school $i",
                        schoolStatus = SchoolStatus.ACTIVE))
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
                schoolId = c1.schools[i%10].id,
                userStatus = UserStatus.ACTIVE
            )
            users.put(user.id, user)
            userRoles.put(user.id, RoleType.USER)
        }

        users.put(u1.id, u1)
        users.put(u2.id, u2)
        users.put(u3.id, u3)

        verificationTokens.put(u1.id, "123")
        verificationTokens.put(u2.id, "678")

        userRoles.put(u1.id, RoleType.USER)
        userRoles.put(u2.id, RoleType.USER)
        userRoles.put(u3.id, RoleType.MODERATOR)

    }

    private fun toDate(birthDate: String) : Date {
        val pattern = "yyyy/MM/dd"
        val format = SimpleDateFormat(pattern)

        return format.parse(birthDate)
    }
}