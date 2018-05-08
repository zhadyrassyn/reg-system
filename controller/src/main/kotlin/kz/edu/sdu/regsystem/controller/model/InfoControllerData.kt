package kz.edu.sdu.regsystem.controller.model

data class AreaData(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String
)

data class GetFacultiesResponseData(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String
)

data class GetSpecialtyResponseData(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String
)

data class GetCitiesAndVillagesResponseData(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val areaId: Long
)