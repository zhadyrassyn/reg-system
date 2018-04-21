package kz.edu.sdu.regsystem.controller.model

data class SchoolData(val schoolId: Long,
                      val nameRu: String,
                      val nameEn: String,
                      val nameKk: String,
                      val schoolStatus: String = "")