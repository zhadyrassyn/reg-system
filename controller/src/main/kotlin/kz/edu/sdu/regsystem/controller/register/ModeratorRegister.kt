package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.EditGeneralInfORequest
import kz.edu.sdu.regsystem.controller.model.GetStudentInfoResponse
import kz.edu.sdu.regsystem.controller.model.GetStudentsResponse

interface ModeratorRegister {

    fun getStudents(currentPage: Int, perPage: Int) : List<GetStudentsResponse>

    fun getStudentInfo(id: Long): GetStudentInfoResponse

    fun editGeneralInfo(id: Long, request: EditGeneralInfORequest)
}