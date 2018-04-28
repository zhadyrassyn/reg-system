package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.model.GetGeneralInfoResponseData
import kz.edu.sdu.regsystem.controller.model.GetPersonalInfoResponse
import kz.edu.sdu.regsystem.controller.model.SavePersonalInfoRequest

interface StudentRegister {
    fun saveGeneralInfo(id: Long, generalInfoData: GeneralInfoData)

    fun getGeneralInfo(id: Long) : GetGeneralInfoResponseData

    fun savePersonalInfo(personalInfo: SavePersonalInfoRequest, id: Long)

    fun getPersonalInfo(id: Long): GetPersonalInfoResponse
}