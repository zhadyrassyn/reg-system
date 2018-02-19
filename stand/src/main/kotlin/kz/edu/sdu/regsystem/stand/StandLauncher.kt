package kz.edu.sdu.regsystem.stand

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["kz.edu.sdu.regsystem"])
class StandLauncher

fun main(args: Array<String>) {
    SpringApplication.run(StandLauncher::class.java, *args)
}