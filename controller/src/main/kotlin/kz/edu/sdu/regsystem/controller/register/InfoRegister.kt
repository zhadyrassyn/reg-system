package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.CityData
import kz.edu.sdu.regsystem.controller.model.SchoolData

interface InfoRegister {
    fun getCities(): List<CityData>

    fun getSchools(cityId: Long): List<SchoolData>
}