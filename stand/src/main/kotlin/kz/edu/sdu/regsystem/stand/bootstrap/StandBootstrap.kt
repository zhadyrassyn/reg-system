package kz.edu.sdu.regsystem.stand.bootstrap

import kz.edu.sdu.regsystem.controller.register.DocumentStorageRegister
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class StandBootstrap(
    val storageRegister: DocumentStorageRegister
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent?) {
        storageRegister.init()
    }

}