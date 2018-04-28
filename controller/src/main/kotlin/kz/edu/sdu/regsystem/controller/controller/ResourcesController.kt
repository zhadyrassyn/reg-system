package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.register.ResourceRegister
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.core.io.InputStreamResource
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api//upload")
class ResourcesController(val resourceRegister: ResourceRegister) {

    @GetMapping
    fun getHello() = "hello"

    @GetMapping("/{fileName}", produces = arrayOf(MediaType.IMAGE_JPEG_VALUE,
        MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE))
    fun getUploadFile(@PathVariable("fileName")fileName: String): ResponseEntity<InputStreamResource>? {
        return resourceRegister.getFile(fileName)
    }


}