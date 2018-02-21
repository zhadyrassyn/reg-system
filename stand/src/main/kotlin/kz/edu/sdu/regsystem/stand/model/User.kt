package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.UserStatus

data class User(var id: Long = -1,
                var email: String,
                var password: String,
                var userStatus: UserStatus)