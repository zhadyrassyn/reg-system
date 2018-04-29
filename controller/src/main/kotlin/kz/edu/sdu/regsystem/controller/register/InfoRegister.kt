package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.*

interface InfoRegister {
    fun getCities(): List<CityData>

    fun getSchools(cityId: Long): List<SchoolData>

    fun getAreas(): List<AreaData>

    fun getFaculties(): List<GetFacultiesResponseData>

    fun getSpecialities(facultyId: Long): List<GetSpecializationsResponseData>

    fun getCitiesAndVillages(areaId: Long): List<GetCitiesAndVillagesResponseData>
}