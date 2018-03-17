package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.CityData
import kz.edu.sdu.regsystem.controller.register.InfoRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import org.springframework.stereotype.Service

@Service
class InfoRegisterStandImpl(
    val db: Db
) : InfoRegister{
    override fun getCities(): List<CityData> {
        return db.cities.values.map { CityData(it.id, it.name) }
    }
}