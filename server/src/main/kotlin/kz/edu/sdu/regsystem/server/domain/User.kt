package kz.edu.sdu.regsystem.server.domain

import kz.edu.sdu.regsystem.server.domain.enums.RoleTypesEnum
import kz.edu.sdu.regsystem.server.domain.enums.UsersStatusEnum
import java.util.*

data class User(var id: Long,
                var email: String,
                var password: String,
                var firstName: String?,
                var middleName: String?,
                var lastName: String?,
                var birthDate: Date?,
                var status: UsersStatusEnum = UsersStatusEnum.NONACTIVE,
                var city: City,
                var school: School,
                var role: RoleTypesEnum = RoleTypesEnum.USER)