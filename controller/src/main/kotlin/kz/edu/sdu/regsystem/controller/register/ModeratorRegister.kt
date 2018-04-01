package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.GetStudentsResponse

interface ModeratorRegister {

    fun getStudents(currentPage: Int, perPage: Int) : List<GetStudentsResponse>
}