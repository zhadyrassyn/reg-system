package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.DocumentData
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.DocumentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.Document
import kz.edu.sdu.regsystem.stand.model.enums.DocumentStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.StorageException
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
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
class DocumentRegisterStandImpl(
    properties: StorageProperties,
    val db: Db) : DocumentRegister {

    private lateinit var rootLocation: Path

    init {
        this.rootLocation = Paths.get(properties.location)
    }

    override fun init() {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    override fun store(file: MultipartFile, documentType: DocumentType, id: Long) {

        val user = db.users.values.firstOrNull { id == it.id } ?:
        throw UserDoesNotExistsException("User does not exist")

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
                filename = "${user.id}_$documentType.$fileExtension"

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Files.copy(file.inputStream, rootLocation.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING)

            val userDocument = Document(
                id = db.longCounter.incrementAndGet(),
                documentType = documentType,
                path = rootLocation.resolve(filename),
                documentStatus = DocumentStatus.WAITING_FOR_RESPONSE
            )

            user.documents.put(documentType, userDocument)
        } catch (e: IOException) {
            throw StorageException("Failed to store file " + filename, e)
        }

    }

    override fun fetchDocumentsStatus(id: Long): List<DocumentData> {
        val user = db.users.values.firstOrNull { id == it.id } ?:
        throw UserDoesNotExistsException("User does not exist")

        return user.documents.values.map {
            DocumentData(
                type = it.documentType.toString(),
                status = it.documentStatus.toString()
            )
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