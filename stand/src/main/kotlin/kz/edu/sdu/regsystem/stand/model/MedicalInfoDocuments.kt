package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.stand.model.enums.CommentStatus

data class MedicalInfoDocuments (
    val comment: String? = "",
    val status: CommentStatus = CommentStatus.WATING_FOR_RESPONSE,
    var form86: Document? = null,
    var form63: Document? = null,
    var flurography: Document? = null
)