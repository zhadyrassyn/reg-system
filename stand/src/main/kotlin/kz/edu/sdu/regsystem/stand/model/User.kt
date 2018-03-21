package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.stand.model.enums.UserStatus
import java.util.*
import kotlin.collections.HashMap

data class User(var id: Long = -1,
                var email: String,
                var password: String,
                var userStatus: UserStatus,
                var firstName: String = "",
                var middleName: String = "",
                var lastName: String = "",
                var birthDate: Date = Date(),
                var cityId: Long = -1,
                var schoolId: Long = -1,
                var documents: HashMap<DocumentType, Document> = HashMap())