package kz.edu.sdu.regsystem.controller.model

import kz.edu.sdu.regsystem.controller.model.enums.AccessType
import java.util.*

data class GetGeneralInfoResponseData(
    val firstName: String? = "",
    val middleName: String? = "",
    val lastName: String? = "",
    val birthDate: Date? = Date(),
    val cityData: CityData? = CityData(-1, ""),
    val schoolData: SchoolData? = SchoolData(-1, ""),
    val accessType: AccessType = AccessType.SAVE
)