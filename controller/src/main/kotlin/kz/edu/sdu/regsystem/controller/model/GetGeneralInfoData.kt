package kz.edu.sdu.regsystem.controller.model

import kz.edu.sdu.regsystem.controller.model.enums.AccessType
import java.util.*

data class GetGeneralInfoResponseData(
    val firstName: String? = "",
    val middleName: String? = "",
    val lastName: String? = "",
    val birthDate: Date? = Date(),
    val city: CityData? = CityData(-1, ""),
    var school: SchoolData? = SchoolData(-1, ""),
    var customSchool: String = "",
    val accessType: AccessType = AccessType.SAVE
)