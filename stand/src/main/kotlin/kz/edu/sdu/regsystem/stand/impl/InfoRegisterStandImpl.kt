package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.AreaData
import kz.edu.sdu.regsystem.controller.model.CityData
import kz.edu.sdu.regsystem.controller.model.SchoolData
import kz.edu.sdu.regsystem.controller.register.InfoRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.enums.AreaType
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class InfoRegisterStandImpl(
    val db: Db
) : InfoRegister{
    @Cacheable("areas")
    override fun getAreas(): List<AreaData> {
        return db.areas.values
            .filter { it.status == AreaType.SYSTEM }
            .map { AreaData(it.id, it.nameRu, it.nameEn, it.nameKk) }
    }

    override fun getSchools(cityId: Long): List<SchoolData> {
        return db.cities[cityId]!!.schools
            .filter { it.schoolStatus == SchoolStatus.ACTIVE }
            .map { SchoolData(it.id, it.name, it.name, it.name) }
    }

    @Cacheable("cities")
    override fun getCities(): List<CityData> {
        return db.cities.values.map { CityData(it.id, it.name, it.name, it.name) }
    }
}