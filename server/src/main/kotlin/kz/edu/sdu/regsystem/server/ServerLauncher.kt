package kz.edu.sdu.regsystem.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["kz.edu.sdu.regsystem"])
//@EnableConfigurationProperties(StorageProperties::class)
class ServerLauncher

fun main(args: Array<String>) {
    runApplication<ServerLauncher>(*args)
}