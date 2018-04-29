package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.InfoRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.enums.AreaType
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class InfoRegisterStandImpl(
    val db: Db
) : InfoRegister {
    @Cacheable("specializagions")
    override fun getSpecializations(facultyId: Long): List<GetSpecializationsResponseData> {
        val faculty = db.faculties.values.firstOrNull { it.id == facultyId }
            ?: throw BadRequestException("Faculty with id $facultyId does not exist")

        return faculty.specializations.values.map {
            GetSpecializationsResponseData(
                id = it.id,
                nameKk = it.nameKk,
                nameEn = it.nameEn,
                nameRu = it.nameRu
            )
        }
    }

    @Cacheable("faculties")
    override fun getFaculties(): List<GetFacultiesResponseData> {
        return db.faculties.values.map {
            GetFacultiesResponseData(
                id = it.id,
                nameRu = it.nameRu,
                nameEn = it.nameEn,
                nameKk = it.nameKk
            )
        }
    }

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