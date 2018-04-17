package kz.edu.sdu.regsystem.server.impl

import kz.edu.sdu.regsystem.controller.model.*
import kz.edu.sdu.regsystem.controller.register.ModeratorRegister
import org.springframework.stereotype.Service

@Service
class ModeratorRegisterServerImpl : ModeratorRegister{
    override fun getStudentInfo(id: Long): GetStudentInfoResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editGeneralInfo(id: Long, request: EditGeneralInfORequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveCommentForDocuments(id: Long, request: SaveCommentForDocumentsRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeDocumentStatus(id: Long, documentId: Long, status: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTotalAmountOfStudents(text: String): FetchTotalAmountOfStudentsResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStudents(text: String, currentPage: Int, perPage: Int): List<GetStudentsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}