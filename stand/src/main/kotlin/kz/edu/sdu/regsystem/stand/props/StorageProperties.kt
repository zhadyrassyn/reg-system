package kz.edu.sdu.regsystem.stand.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("storage")
class StorageProperties {
    var location = "stand/src/main/resources/upload-dir"
}