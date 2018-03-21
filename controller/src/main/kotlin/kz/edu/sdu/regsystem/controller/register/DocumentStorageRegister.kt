package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import org.springframework.web.multipart.MultipartFile

interface DocumentStorageRegister {
    fun init();

    fun store(file: MultipartFile, documentType: DocumentType, authToken: String)
}