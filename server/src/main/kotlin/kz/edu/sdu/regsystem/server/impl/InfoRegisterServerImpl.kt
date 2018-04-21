package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.CityData
import kz.edu.sdu.regsystem.controller.model.SchoolData
import kz.edu.sdu.regsystem.controller.register.InfoRegister
import kz.edu.sdu.regsystem.server.domain.enums.SchoolStatusEnum
import kz.edu.sdu.regsystem.server.repositoy.InfoRepository
import org.springframework.stereotype.Service

@Service
class InfoRegisterServerImpl(val infoRepository: InfoRepository) : InfoRegister {
    override fun getSchools(cityId: Long): List<SchoolData> {
        return infoRepository.getSchoolsByCityAndStatus(
            cityId = cityId,
            status = SchoolStatusEnum.ACTIVE.name
        ).map {
            SchoolData(
                schoolId = it.id,
                nameRu = it.nameRu,
                nameEn = it.nameEn,
                nameKk = it.nameKk,
                schoolStatus = it.status.name
            )
        }
    }

    override fun getCities(): List<CityData> {
        return infoRepository.getCities()
            .map {
                CityData(
                    id = it.id,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    nameKk = it.nameKk
                )
            }
    }

}