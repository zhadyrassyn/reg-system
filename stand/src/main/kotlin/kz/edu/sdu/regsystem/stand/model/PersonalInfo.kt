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

    val birthPlace: Area,
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

    var ud_front : Document? = null,
    var ud_back: Document? = null,
    var photo3x4: Document? = null
)