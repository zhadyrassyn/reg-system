package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.InfoRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.enums.AreaType
import kz.edu.sdu.regsystem.stand.model.enums.SchoolStatus
import kz.edu.sdu.regsystem.stand.model.enums.UserCityStatus
import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class InfoRegisterStandImpl(
    val db: Db
) : InfoRegister {
    @Cacheable("schools")
    override fun getSchools(areaId: Long, cityId: Long): List<SchoolData> {
        val area = db.areas[areaId] ?: throw BadRequestException("Cannot find area with id $areaId")
        val city = area.cities[cityId] ?: throw BadRequestException("Cannot find city/village with id $cityId")

        return city.schools.values
            .filter { it.schoolStatus == SchoolStatus.SYSTEM }
            .map {
                SchoolData(
                    id = it.id,
                    nameEn = it.nameEn,
                    nameRu = it.nameRu,
                    nameKk = it.nameKk,
                    cityId = city.id
                )
            }
    }

    @Cacheable("cities")
    override fun getCitiesAndVillages(areaId: Long): List<GetCitiesAndVillagesResponseData> {
        val area = db.areas.values.firstOrNull { it.id == areaId }
            ?: throw BadRequestException("Area with id $areaId does not exist")

        return area.cities.values
            .filter { it.status == UserCityStatus.SYSTEM }
            .map {
                GetCitiesAndVillagesResponseData(
                    id = it.id,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    nameKk = it.nameKk,
                    areaId = area.id
                )
            }
    }

    @Cacheable("specializagions")
    override fun getSpecialities(facultyId: Long): List<GetSpecialtyResponseData> {
        val faculty = db.faculties.values.firstOrNull { it.id == facultyId }
            ?: throw BadRequestException("Faculty with id $facultyId does not exist")

        return faculty.specializations.values.map {
            GetSpecialtyResponseData(
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
}