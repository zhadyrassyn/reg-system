package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.*

interface InfoRegister {
    fun getAreas(): List<AreaData>

    fun getCitiesAndVillages(areaId: Long): List<GetCitiesAndVillagesResponseData>

    fun getSchools(areaId: Long, cityId: Long): List<SchoolData>

    fun getFaculties(): List<GetFacultiesResponseData>

    fun getSpecialities(facultyId: Long): List<GetSpecialtyResponseData>
}