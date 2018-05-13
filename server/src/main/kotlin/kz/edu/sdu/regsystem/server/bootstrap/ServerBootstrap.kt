package kz.edu.sdu.regsystem.server.bootstrap

import kz.edu.sdu.regsystem.server.services.FileService
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class ServerBootstrap(val fileService: FileService) : ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent?) {
        fileService.init()
    }
}