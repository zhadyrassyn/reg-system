package kz.edu.sdu.regsystem.stand.service

import kz.edu.sdu.regsystem.controller.model.DocumentData
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.DocumentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.Document
import kz.edu.sdu.regsystem.stand.model.enums.DocumentStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.StorageException
import kz.edu.sdu.regsystem.stand.props.StorageProperties
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileService(
    properties: StorageProperties,
    val db: Db
)  {
    private lateinit var rootLocation: Path

    init {
        this.rootLocation = Paths.get(properties.location)
    }

    fun init() {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    fun store(file: MultipartFile, documentType: DocumentType, id: Long) : Document? {
        var filename = StringUtils.cleanPath(file.originalFilename)

        try {
            if(file.isEmpty) {
                throw StorageException("Failed to store empty file $filename")
            }

            if(filename.contains("..")) {
                // This is a security check
                throw StorageException("Cannot store file with relative path outside current directory $filename")
            }

            try {
                val fileExtension = getFileExtension(filename)
                filename = "${id}_$documentType.$fileExtension"

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Files.copy(file.inputStream, rootLocation.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING)

            return Document(
                id = db.longCounter.incrementAndGet(),
                documentType = documentType,
                path = rootLocation.resolve(filename),
                documentStatus = DocumentStatus.WAITING_FOR_RESPONSE
            )

        } catch (e: IOException) {
            throw StorageException("Failed to store file " + filename, e)
        }
    }

    private fun getFileExtension(filename: String): String {
        val index = filename.lastIndexOf('.')
        if(index > 0) {
            return filename.substring(index + 1)
        }
        throw RuntimeException("Cannot find extension of $filename")
    }

}