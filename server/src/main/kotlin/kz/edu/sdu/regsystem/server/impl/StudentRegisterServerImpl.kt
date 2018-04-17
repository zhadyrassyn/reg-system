package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.GetGeneralInfoResponseData
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import org.springframework.stereotype.Service

@Service
class StudentRegisterServerImpl : StudentRegister {
    override fun saveGeneralInfo(authToken: String, generalInfoData: GeneralInfoData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGeneralInfo(authToken: String): GetGeneralInfoResponseData {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}