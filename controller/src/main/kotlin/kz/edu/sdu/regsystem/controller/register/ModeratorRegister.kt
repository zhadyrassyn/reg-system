package kz.edu.sdu.regsystem.controller.register

import kz.edu.sdu.regsystem.controller.model.*

interface ModeratorRegister {

    fun getStudents(currentPage: Int, perPage: Int) : List<GetStudentsResponse>

    fun getStudentInfo(id: Long): GetStudentInfoResponse

    fun editGeneralInfo(id: Long, request: EditGeneralInfORequest)

    fun saveCommentForDocuments(id: Long, request: SaveCommentForDocumentsRequest)

    fun changeDocumentStatus(id: Long, documentId: Long, status: String)

    fun search(text: String): List<GetStudentsResponse>
}