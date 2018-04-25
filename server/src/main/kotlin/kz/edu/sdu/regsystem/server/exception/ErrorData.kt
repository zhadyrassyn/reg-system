package kz.edu.sdu.regsystem.server.exception

/* BAD REQUEST EXCEPTIONS */
open class BadRequestException : Exception {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable) : super(message, error)
}

open class UserAlreadyExistsException:  BadRequestException {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable): super(message, error)
}

open class VerificationTokenDoesNotExistsException:  BadRequestException {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable): super(message, error)
}

open class UserDoesNotExistsException:  BadRequestException {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable): super(message, error)
}

/* INTERNAL SERVER ERROR */
open class InternalServerError : Exception {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable) : super(message, error)
}

open class BootstrapException : InternalServerError {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable): super(message, error)
}

data class ErrorResponse(val statusCode: Int,
                         val statusMessage: String,
                         val message: String?)