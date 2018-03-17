package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.CityData

interface InfoRegister {
    fun getCities(): List<CityData>
}