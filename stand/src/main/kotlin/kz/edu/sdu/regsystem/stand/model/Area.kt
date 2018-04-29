package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.AreaType

data class Area(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val status: AreaType = AreaType.SYSTEM,
    val cities: HashMap<Long, UserCity> = HashMap()
)