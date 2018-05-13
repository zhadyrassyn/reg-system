package kz.edu.sdu.regsystem.server.props

import org.springframework.boot.context.properties.ConfigurationProperties
import java.nio.file.Paths

@ConfigurationProperties("storage")
class StorageProperties {
    val path = Paths.get("").toRealPath().toString()
    var location =
        if (path.contains("server")) "$path/src/main/resources/upload-dir"
        else "$path/server/src/main/resources/upload-dir"
}