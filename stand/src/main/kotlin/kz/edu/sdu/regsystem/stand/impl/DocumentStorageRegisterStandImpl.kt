package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.DocumentStorageRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.impl.storage.StorageProperties
import kz.edu.sdu.regsystem.stand.model.exceptions.StorageException
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class DocumentStorageRegisterStandImpl(
    val properties: StorageProperties,
    val db: Db,
    val env: Environment) : DocumentStorageRegister {

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

    override fun store(file: MultipartFile, documentType: DocumentType, authToken: String) {
//        val token = authToken.substring(7)
//        val jwtKey = env.getProperty("jwtKey")
//
//        val email = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).body.subject
//            ?: throw SignatureException("Cannot parse jwt.")
//
//
//        val user = db.users.values.firstOrNull { it -> it.email == email } ?:
//        throw UserDoesNotExistsException("User does not exist")

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
                filename = "1_$documentType.$fileExtension"

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Files.copy(file.inputStream, rootLocation.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING)
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