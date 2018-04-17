package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.CityData
import kz.edu.sdu.regsystem.controller.model.SchoolData
import kz.edu.sdu.regsystem.controller.register.InfoRegister
import org.springframework.stereotype.Service

@Service
class InfoRegisterServerImpl : InfoRegister{
    override fun getSchools(cityId: Long): List<SchoolData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCities(): List<CityData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}