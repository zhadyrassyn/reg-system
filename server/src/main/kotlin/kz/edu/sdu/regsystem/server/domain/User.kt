package kz.edu.sdu.regsystem.server.domain

import kz.edu.sdu.regsystem.server.domain.enums.RoleTypesEnum
import kz.edu.sdu.regsystem.server.domain.enums.UsersStatusEnum
import java.util.*

data class User(var id: Long,
                var email: String? = null,
                var password: String? = null,
                var firstName: String? = null,
                var middleName: String? = null,
                var lastName: String? = null,
                var birthDate: Date? = null,
                var status: UsersStatusEnum = UsersStatusEnum.NONACTIVE,
                var city: City? = null,
                var school: School? = null,
                var role: RoleTypesEnum = RoleTypesEnum.USER)