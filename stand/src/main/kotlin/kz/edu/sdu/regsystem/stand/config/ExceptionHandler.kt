package kz.edu.sdu.regsystem.stand.config

import kz.edu.sdu.regsystem.stand.model.exceptions.BadRequestException
import kz.edu.sdu.regsystem.stand.model.exceptions.ErrorResponse
import kz.edu.sdu.regsystem.stand.model.exceptions.ForbiddenException
import kz.edu.sdu.regsystem.stand.model.exceptions.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BadRequestException::class)
    fun onBadRequest(ex: BadRequestException) : ResponseEntity<ErrorResponse> {
        val httpError = HttpStatus.BAD_REQUEST
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message)
        return ResponseEntity(errorResponse, httpError)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun forbidden(ex: ForbiddenException) : ResponseEntity<ErrorResponse> {
        val httpError = HttpStatus.FORBIDDEN
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message)
        return ResponseEntity(errorResponse, httpError)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorized(ex: UnauthorizedException) : ResponseEntity<ErrorResponse> {
        val httpError = HttpStatus.UNAUTHORIZED
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message)
        return ResponseEntity(errorResponse, httpError)
    }

    @ExceptionHandler(Exception::class)
    fun onUnkownError(ex: Exception) : ResponseEntity<ErrorResponse> {
        val httpError = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message ?:
        "Internal Server Error")
        return ResponseEntity(errorResponse, httpError)
    }
}