package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.register.ResourceRegister
import kz.edu.sdu.regsystem.stand.props.StorageProperties
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class ResourceRegisterStandImpl(
    properties: StorageProperties) : ResourceRegister{

    private lateinit var rootLocation: Path

    init {
        this.rootLocation = Paths.get(properties.location)
    }

    override fun getFile(fileName: String): ResponseEntity<InputStreamResource>? {
        val file = FileInputStream(File(rootLocation.resolve(fileName).toUri()))

        return ResponseEntity
            .ok()
            .body(InputStreamResource(file))
    }

}