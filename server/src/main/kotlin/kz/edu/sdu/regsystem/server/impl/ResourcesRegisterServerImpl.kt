package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.register.ResourceRegister
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ResourcesRegisterServerImpl : ResourceRegister {
    override fun getFile(fileName: String): ResponseEntity<InputStreamResource>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}