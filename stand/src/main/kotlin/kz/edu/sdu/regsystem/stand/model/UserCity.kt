package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.UserCityStatus

data class UserCity(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val status: UserCityStatus = UserCityStatus.SYSTEM
)