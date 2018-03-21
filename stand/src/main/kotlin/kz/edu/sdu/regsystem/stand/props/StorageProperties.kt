package kz.edu.sdu.regsystem.stand.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("storage")
class StorageProperties {
    var location = "upload-dir"
}