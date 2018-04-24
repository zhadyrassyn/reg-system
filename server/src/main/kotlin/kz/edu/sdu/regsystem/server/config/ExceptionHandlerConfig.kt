package kz.edu.sdu.regsystem.server.config

import kz.edu.sdu.regsystem.server.exception.BadRequestException
import kz.edu.sdu.regsystem.server.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandlerConfig : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BadRequestException::class)
    fun onBadRequest(ex: BadRequestException) : ResponseEntity<ErrorResponse> {
        logger.error(ex.message, ex)

        val httpError = HttpStatus.BAD_REQUEST
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message)
        return ResponseEntity(errorResponse, httpError)
    }

    @ExceptionHandler(Exception::class)
    fun onUnkownError(ex: Exception) : ResponseEntity<ErrorResponse> {
        logger.error(ex.message, ex)

        val httpError = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message
            ?: "Internal Server Error")
        return ResponseEntity(errorResponse, httpError)
    }
}