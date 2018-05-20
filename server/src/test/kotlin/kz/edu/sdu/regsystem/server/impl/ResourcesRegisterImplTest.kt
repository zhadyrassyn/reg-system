package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.server.props.StorageProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.annotations.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@SpringBootTest
class ResourcesRegisterImplTest : AbstractTestNGSpringContextTests(){

    @Autowired
    lateinit var resourcesRegisterImpl: ResourcesRegisterImpl

    var properties: StorageProperties = StorageProperties()

    lateinit public var rootLocation: Path

    init {
        this.rootLocation = Paths.get(properties.location)
    }

    @Test
    fun getFile() {
        //saving file to upload-dir dir
        val fileName = "testimg.png"
        val source = File("${System.getProperty("user.home")}/test/$fileName")
        println(rootLocation)
        Files.copy(source.inputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING)

        //
        //
        val file = resourcesRegisterImpl.getFile(fileName)
        //
        //

        assertNotNull(file)
        assertEquals(file?.statusCode.toString(), "200")
        assertEquals(Files.readAllBytes(rootLocation.resolve(fileName)), Files.readAllBytes(source.toPath()))

    }

}