package kz.edu.sdu.regsystem.stand

import kz.edu.sdu.regsystem.stand.impl.storage.StorageProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["kz.edu.sdu.regsystem"])
@EnableConfigurationProperties(StorageProperties::class)
class StandLauncher

fun main(args: Array<String>) {
    SpringApplication.run(StandLauncher::class.java, *args)
}

