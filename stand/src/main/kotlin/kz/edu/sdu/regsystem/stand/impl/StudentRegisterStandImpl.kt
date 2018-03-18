package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.GeneralInfoData
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import org.springframework.stereotype.Service

@Service
class StudentRegisterStandImpl(
    val db: Db
) : StudentRegister{
    override fun saveGeneralInfo(generalInfoData: GeneralInfoData) {
        println(generalInfoData)
    }

}