package kz.edu.sdu.regsystem.stand.bootstrap

import kz.edu.sdu.regsystem.controller.register.DocumentRegister
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class StandBootstrap(
    val documentRegister: DocumentRegister
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent?) {
        documentRegister.init()
    }

}