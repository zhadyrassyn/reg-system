package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus

data class School(var id: Long,
                  var name: String,
                  var schoolStatus: SchoolStatus = SchoolStatus.NONACTIVE)