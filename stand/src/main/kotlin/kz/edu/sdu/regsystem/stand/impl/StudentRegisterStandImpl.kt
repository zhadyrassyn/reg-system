package kz.edu.sdu.regsystem.stand.impl

import kz.edu.sdu.regsystem.controller.model.Document
import kz.edu.sdu.regsystem.controller.model.GetPersonalInfoResponse
import kz.edu.sdu.regsystem.controller.model.SavePersonalInfoRequest
import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.controller.register.StudentRegister
import kz.edu.sdu.regsystem.stand.impl.db.Db
import kz.edu.sdu.regsystem.stand.model.Area
import kz.edu.sdu.regsystem.stand.model.PersonalInfo
import kz.edu.sdu.regsystem.stand.model.enums.AreaType
import kz.edu.sdu.regsystem.stand.model.exceptions.UserDoesNotExistsException
import kz.edu.sdu.regsystem.stand.service.FileService
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat
import java.util.*

@Service
class StudentRegisterStandImpl(
    val db: Db,
    val fileService: FileService
) : StudentRegister{
    override fun savePersonalInfoDocument(id: Long, file: MultipartFile, documentType: DocumentType) : Document{
        val user = db.users.values.firstOrNull { id == it.id } ?:
        throw UserDoesNotExistsException("User does not exist")

        var savedFile: kz.edu.sdu.regsystem.stand.model.Document? = null
        try {
            savedFile = fileService.store(file=file, documentType = documentType, id = id)
            if(documentType == DocumentType.IDENTITY_CARD_FRONT) {
                user.personalInfoDocuments.ud_front = savedFile
            } else if(documentType == DocumentType.IDENTITY_CARD_BACK) {
                user.personalInfoDocuments.ud_back = savedFile
            } else if(documentType == DocumentType.PHOTO_3x4) {
                user.personalInfoDocuments.photo3x4 = savedFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Document(name = savedFile!!.path!!.fileName.toString())
    }

    override fun getPersonalInfo(id: Long): GetPersonalInfoResponse? {
        val user = db.users.values.firstOrNull { it -> it.id == id } ?:
        throw UserDoesNotExistsException("User does not exist")

        if(user.personalInfo == null) {
            return GetPersonalInfoResponse(
                ud_front = user.personalInfoDocuments.ud_front?.path?.fileName?.toString(),
                ud_back = user.personalInfoDocuments.ud_back?.path?.fileName?.toString(),
                photo3x4 = user.personalInfoDocuments.photo3x4?.path?.fileName?.toString()
            )
        }

        val personalInfo = user.personalInfo

        // if birth place is custom value
        val birthPlaceCustom: String? =
            if (personalInfo!!.birthPlace.status == AreaType.CUSTOM)
                personalInfo.birthPlace.nameRu
            else
                null
        return GetPersonalInfoResponse(
            id = user.id,
            firstName = personalInfo.firstName,
            middleName = personalInfo.middleName,
            lastName = personalInfo.lastName,
            gender = personalInfo.gender,
            birthDate = toDate(personalInfo.birthDate),
            givenDate = toDate(personalInfo.givenDate),
            givenPlace = personalInfo.givenPlace,
            iin = personalInfo.iin,
            ud_number = personalInfo.ud_number,
            mobilePhone = personalInfo.mobilePhone,
            telPhone = personalInfo.telPhone,
            nationality = personalInfo.nationality,
            birthPlace = personalInfo.birthPlace.id,
            birthPlaceCustom = birthPlaceCustom,
            blood_group = personalInfo.blood_group,
            citizenship = personalInfo.citizenship,
            factFlat = personalInfo.factFlat,
            factFraction = personalInfo.factFraction,
            factHouse = personalInfo.factHouse,
            factStreet = personalInfo.factStreet,
            regFlat = personalInfo.regFlat,
            regFraction = personalInfo.regFraction,
            regHouse = personalInfo.regHouse,
            regStreet = personalInfo.regStreet,
            ud_front = user.personalInfoDocuments.ud_front?.path?.fileName?.toString(),
            ud_back = user.personalInfoDocuments.ud_back?.path?.fileName?.toString(),
            photo3x4 = user.personalInfoDocuments.photo3x4?.path?.fileName?.toString()
        )
    }

    override fun savePersonalInfo(personalInfo: SavePersonalInfoRequest, id: Long) {
        println("Data $personalInfo")
        val user = db.users.values.firstOrNull { it -> it.id == id } ?:
        throw UserDoesNotExistsException("User does not exist")

        //save new are if birthPlaceCustom field is not null
        var areaId = personalInfo.birthPlace

        try {
            if(Objects.isNull(personalInfo.birthPlace) && !StringUtils.isEmpty(personalInfo.birthPlaceCustom)) {
                val area = Area(
                    id = db.longCounter.incrementAndGet(),
                    nameKk = personalInfo.birthPlaceCustom!!,
                    nameEn = personalInfo.birthPlaceCustom!!,
                    nameRu = personalInfo.birthPlaceCustom!!,
                    status = AreaType.CUSTOM
                )

                db.areas.put(area.id, area)
                areaId = area.id
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        user.personalInfo = PersonalInfo(
            firstName = personalInfo.firstName,
            middleName = personalInfo.middleName,
            lastName = personalInfo.lastName,
            gender = personalInfo.gender,
            birthDate = personalInfo.birthDate,
            givenDate = personalInfo.givenDate,
            givenPlace = personalInfo.givenPlace,
            iin = personalInfo.iin,
            ud_number = personalInfo.ud_number,
            mobilePhone = personalInfo.mobilePhone,
            telPhone = personalInfo.telPhone,
            nationality = personalInfo.nationality,
            birthPlace = db.areas.values.first { it.id == areaId },
            blood_group = personalInfo.blood_group,
            citizenship = personalInfo.citizenship,
            factFlat = personalInfo.factFlat,
            factFraction = personalInfo.factFraction,
            factHouse = personalInfo.factHouse,
            factStreet = personalInfo.factStreet,
            regFlat = personalInfo.regFlat,
            regFraction = personalInfo.regFraction,
            regHouse = personalInfo.regHouse,
            regStreet = personalInfo.regStreet
        )
    }

    private fun toDate(birthDate: Date) : String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(birthDate)
    }

}