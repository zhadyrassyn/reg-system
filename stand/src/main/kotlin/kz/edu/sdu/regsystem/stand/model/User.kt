package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import java.util.*

data class User(var id: Long = -1,
                var email: String,
                var password: String,
                var userStatus: UserStatus,
                var firstName: String = "",
                var middleName: String = "",
                var lastName: String = "",
                var birthDate: Date = Date(),
                var cityId: Long = -1,
                var schoolId: Long = -1)