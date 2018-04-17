package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.DocumentData
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.DocumentRegister
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentRegisterServerImpl : DocumentRegister{
    override fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun store(file: MultipartFile, documentType: DocumentType, authToken: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchDocumentsStatus(authToken: String): List<DocumentData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}