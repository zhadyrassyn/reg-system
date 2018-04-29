package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus

data class School(var id: Long,
                  var nameRu: String,
                  var nameKk: String,
                  var nameEn: String,
                  var schoolStatus: SchoolStatus = SchoolStatus.SYSTEM)