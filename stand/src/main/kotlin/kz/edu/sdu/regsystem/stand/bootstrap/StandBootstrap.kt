package kz.edu.sdu.regsystem.stand.bootstrap

import kz.edu.sdu.regsystem.stand.service.FileService
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class StandBootstrap(
    val FileService: FileService
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent?) {
        FileService.init()
    }

}