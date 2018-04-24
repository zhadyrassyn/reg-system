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