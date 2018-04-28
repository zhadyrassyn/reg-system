package kz.edu.sdu.regsystem.stand.model
import java.util.Date

data class PersonalInfo(
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val gender: String,

    val birthDate: Date,
    val givenDate: Date,
    val givenPlace: String,
    val iin: String,
    val ud_number: String,

    val mobilePhone: String,
    val telPhone: String? = null,
    val nationality: String,

    val birthPlace: Long,
    val birthPlaceCustom: String? = null,
    val blood_group: Long? = null,
    val citizenship: String,
    val factFlat: String? = null,
    val factFraction: String? = null,
    val factHouse: String,
    val factStreet: String,

    val regFlat: String? = null,
    val regFraction: String? = null,
    val regHouse: String,
    val regStreet: String,

    val ud_front : Document? = null,
    val ud_back: Document? = null,
    val photo3x4: Document? = null
)