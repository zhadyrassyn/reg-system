package kz.edu.sdu.regsystem.server.domain

import kz.edu.sdu.regsystem.server.domain.enums.SchoolStatusEnum

data class School(var id: Long,
                  var nameRu: String? = "",
                  var nameEn: String? = "",
                  var nameKk: String? = "",
                  var status: SchoolStatusEnum = SchoolStatusEnum.NONACTIVE,
                  var cityId: Long = 0)