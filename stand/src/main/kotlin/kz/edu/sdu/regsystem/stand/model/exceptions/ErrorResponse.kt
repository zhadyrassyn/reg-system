package kz.edu.sdu.regsystem.stand.model.exceptions

data class ErrorResponse(val statusCode: Int,  val statusMessage: String, val message: String)