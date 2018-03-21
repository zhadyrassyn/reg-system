package kz.edu.sdu.regsystem.stand.impl.storage

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("storage")
class StorageProperties {
    var location = "upload-dir"
}