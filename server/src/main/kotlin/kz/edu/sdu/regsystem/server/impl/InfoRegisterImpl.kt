package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.InfoRegister
import kz.edu.sdu.regsystem.server.domain.enums.SchoolStatusEnum
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import org.springframework.stereotype.Service

@Service
class InfoRegisterImpl(val infoRepository: InfoRepository) : InfoRegister {
    override fun getAreas(): List<AreaData> {
        return infoRepository.getAreas()
            .map {
                AreaData(
                    id = it.id,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    nameKk = it.nameKk
                )
            }
    }

    override fun getCitiesAndVillages(areaId: Long): List<GetCitiesAndVillagesResponseData> {
        return infoRepository.getCities()
            .map {
                GetCitiesAndVillagesResponseData(
                    id = it.id,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    nameKk = it.nameKk,
                    areaId = it.areaId
                )
            }
    }

    override fun getSchools(areaId: Long, cityId: Long): List<SchoolData> {
        return infoRepository.getSchools()
            .map {
                SchoolData(
                    id = it.id,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    nameKk = it.nameKk,
                    cityId = it.cityId
                )
            }
    }

    override fun getFaculties(): List<GetFacultiesResponseData> {
        return infoRepository.getFaculties()
            .map {
                GetFacultiesResponseData(
                    id = it.id,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    nameKk = it.nameKk
                )
            }
    }

    override fun getSpecialities(facultyId: Long): List<GetSpecialtyResponseData> {
        return infoRepository.getSpecialties()
            .map {
                GetSpecialtyResponseData(
                    id = it.id,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    nameKk = it.nameKk
                )
            }
    }

}