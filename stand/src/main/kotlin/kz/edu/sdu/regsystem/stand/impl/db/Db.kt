package kz.edu.sdu.regsystem.stand.impl.db

import kz.edu.sdu.regsystem.stand.model.User
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class Db {
    val users = HashMap<Long, User>()
    val longCounter = AtomicLong()

    init {
        val u1 = User(
                id = longCounter.incrementAndGet(),
                email = "dandibobo537@gmail.com",
                password = "qwerty",
                userStatus = UserStatus.ACTIVE)

        val u2 = User(
                email = "notActive@gmail.com",
                password = "qwerty",
                userStatus = UserStatus.NONACTIVE)

        users.put(u1.id, u1)
        users.put(u2.id, u2)
    }
}