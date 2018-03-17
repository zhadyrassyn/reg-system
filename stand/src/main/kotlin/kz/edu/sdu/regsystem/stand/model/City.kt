package kz.edu.sdu.regsystem.stand.model

data class City(var id: Long,
                var name: String,
                var schools: ArrayList<School> = ArrayList())