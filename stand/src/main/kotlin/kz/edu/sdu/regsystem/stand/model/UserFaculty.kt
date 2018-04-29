package kz.edu.sdu.regsystem.stand.model

data class UserFaculty(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val nameKk: String,

    var specializations: HashMap<Long, Speciality> = HashMap()
)