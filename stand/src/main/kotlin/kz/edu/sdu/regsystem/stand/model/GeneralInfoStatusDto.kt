package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.GeneralInfoStatus

data class GeneralInfoStatusDto(
    var comment: String? = "",
    var status: GeneralInfoStatus = GeneralInfoStatus.WAITING_FOR_RESPONSE
)