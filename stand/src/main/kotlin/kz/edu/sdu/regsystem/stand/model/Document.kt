package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.stand.model.enums.DocumentStatus
import java.nio.file.Path

data class Document(var id: Long,
                    var documentType: DocumentType,
                    var path: Path? = null,
                    var documentStatus: DocumentStatus = DocumentStatus.NOT_SEND)