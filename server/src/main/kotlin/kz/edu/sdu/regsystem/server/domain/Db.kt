package kz.edu.sdu.regsystem.server.domain

import kz.edu.sdu.regsystem.server.domain.enums.AreaType

data class Area(
    var id: Long = -1,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,
    val type: AreaType = AreaType.SYSTEM
)