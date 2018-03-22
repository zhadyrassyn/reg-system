package kz.edu.sdu.regsystem.stand.model

import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.stand.model.enums.DocumentStatus
import java.nio.file.Path

data class Document(val id: Long,
                    val documentType: DocumentType,
                    val path: Path,
                    val documentStatus: DocumentStatus = DocumentStatus.NOT_SEND)