package kz.edu.sdu.regsystem.controller.register

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity

interface ResourceRegister {
    fun getFile(fileName: String) : ResponseEntity<InputStreamResource>?
}