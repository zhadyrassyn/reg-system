package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.GetGeneralInfoResponseData

interface StudentRegister {
    fun saveGeneralInfo(authToken: String, generalInfoData: GeneralInfoData)

    fun getGeneralInfo(authToken: String) : GetGeneralInfoResponseData
}