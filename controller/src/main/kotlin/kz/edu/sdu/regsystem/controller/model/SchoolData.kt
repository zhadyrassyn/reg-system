package kz.edu.sdu.regsystem.controller.model

data class SchoolData(val id: Long,
                      val nameRu: String?,
                      val nameEn: String?,
                      val nameKk: String?,
                      val cityId: Long? = 0)