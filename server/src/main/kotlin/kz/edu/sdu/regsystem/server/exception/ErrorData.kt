package kz.edu.sdu.regsystem.server.exception

import java.lang.RuntimeException

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

open class PasswordMismatchException:  BadRequestException {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable): super(message, error)
}

/* UNAUTHORIZED ERROS */
open class Unauthorized : Exception {
    constructor(message: String) : super(message)

    constructor(message: String, error: Throwable) : super(message, error)
}

open class UserNotConfirmedException:  Unauthorized {
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

/* RUNTIME EXCEPTIONS */
class StorageException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}