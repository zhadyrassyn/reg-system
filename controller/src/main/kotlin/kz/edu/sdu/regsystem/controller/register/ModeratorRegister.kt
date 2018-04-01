package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.GetStudentsResponse

interface ModeratorRegister {

    fun getStudents() : List<GetStudentsResponse>
}