package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.UserStatus

data class User(val id: Long = -1,
                val email: String,
                val password: String,
                val userStatus: UserStatus)