package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.DocumentData
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import org.springframework.web.multipart.MultipartFile

interface DocumentRegister {
    fun init();

    fun store(file: MultipartFile, documentType: DocumentType, id: Long)

    fun fetchDocumentsStatus(id: Long) : List<DocumentData>
}