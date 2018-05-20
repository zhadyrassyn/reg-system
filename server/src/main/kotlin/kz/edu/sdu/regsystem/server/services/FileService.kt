package kz.edu.sdu.regsystem.server.services

import kz.edu.sdu.regsystem.server.exception.StorageException
import kz.edu.sdu.regsystem.server.props.StorageProperties
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class FileService(
    properties: StorageProperties
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

    fun store(file: MultipartFile) : String {
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
                filename = "${UUID.randomUUID().toString()}.$fileExtension"

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Files.copy(file.inputStream, rootLocation.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING)

        } catch (e: IOException) {
            throw StorageException("Failed to store file " + filename, e)
        }

        return filename
    }

    fun deleteFile(filename: String) {
        Files.deleteIfExists(rootLocation.resolve(filename))
    }

    fun getFile(fileName: String) : File {
        return File(rootLocation.resolve(fileName).toUri())
    }

    fun getFilePath(filename: String) : Path {
        return rootLocation.resolve(filename)
    }

    private fun getFileExtension(filename: String): String {
        val index = filename.lastIndexOf('.')
        if(index > 0) {
            return filename.substring(index + 1)
        }
        throw RuntimeException("Cannot find extension of $filename")
    }

}