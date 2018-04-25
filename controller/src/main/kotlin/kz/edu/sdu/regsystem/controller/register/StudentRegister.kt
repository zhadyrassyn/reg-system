package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.GetGeneralInfoResponseData

interface StudentRegister {
    fun saveGeneralInfo(id: Long, generalInfoData: GeneralInfoData)

    fun getGeneralInfo(id: Long) : GetGeneralInfoResponseData
}