package kz.edu.sdu.regsystem.server.domain

import kz.edu.sdu.regsystem.server.domain.enums.ExistType

data class Area(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val type: ExistType = ExistType.SYSTEM
)

data class City(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val type: ExistType = ExistType.SYSTEM,
    val areaId: Long
)

data class School(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val type: ExistType = ExistType.SYSTEM,
    val cityId: Long
)

data class Faculty(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String
)