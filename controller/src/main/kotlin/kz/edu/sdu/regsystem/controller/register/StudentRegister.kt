package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData

interface StudentRegister {
    fun saveGeneralInfo(authToken: String, generalInfoData: GeneralInfoData)
}