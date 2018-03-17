package kz.edu.sdu.regsystem.stand.impl.db

import kz.edu.sdu.regsystem.stand.model.City
import kz.edu.sdu.regsystem.stand.model.User
import kz.edu.sdu.regsystem.stand.model.enums.RoleType
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class Db {
    val users = HashMap<Long, User>()
    val longCounter = AtomicLong()
    val verificationTokens = HashMap<Long, String>()
    val userRoles = HashMap<Long, RoleType>()
    val cities = HashMap<Long, City>()

    init {
        val u1 = User(
                id = longCounter.incrementAndGet(),
                email = "dandibobo537@gmail.com",
                password = "qwerty",
                userStatus = UserStatus.ACTIVE)

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

        users.put(u1.id, u1)
        users.put(u2.id, u2)
        users.put(u3.id, u3)

        verificationTokens.put(u1.id, "123")
        verificationTokens.put(u2.id, "678")

        userRoles.put(u1.id, RoleType.USER)
        userRoles.put(u2.id, RoleType.USER)
        userRoles.put(u3.id, RoleType.MODERATOR)

        //fill cities
        val c1 = City(longCounter.incrementAndGet(), "Almaty")
        val c2 = City(longCounter.incrementAndGet(), "Astana")
        val c3 = City(longCounter.incrementAndGet(), "Qyzylorda")
        val c4 = City(longCounter.incrementAndGet(), "Shymkent")
        val c5 = City(longCounter.incrementAndGet(), "Aktobe")
        val c6 = City(longCounter.incrementAndGet(), "Taldykorgan")
        val c7 = City(longCounter.incrementAndGet(), "Pavlodar")

        cities[c1.id] = c1
        cities[c2.id] = c2
        cities[c3.id] = c3
        cities[c4.id] = c4
        cities[c5.id] = c5
        cities[c6.id] = c6
        cities[c7.id] = c7
    }
}