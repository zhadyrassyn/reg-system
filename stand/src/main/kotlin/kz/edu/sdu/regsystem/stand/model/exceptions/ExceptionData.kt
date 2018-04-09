package kz.edu.sdu.regsystem.stand.model.exceptions

import java.lang.RuntimeException

/* BAD REQUEST EXCEPTIONS */
open class BadRequestException (override var message: String): Exception(message)

open class UserDoesNotExistsException (override var message: String): BadRequestException(message)

open class UserAlreadyExistsException (override var message: String): BadRequestException(message)

open class CityDoesNotExistException (override var message: String) : BadRequestException(message)

open class SchoolDoesNotExistException (override var message: String) : BadRequestException(message)

/* END BAD REQUEST EXCEPTIONS */

/* FORBIDDEN EXCEPTIONS */
open class ForbiddenException (override var message: String): Exception(message)

open class UserNotActiveException (override var message: String): ForbiddenException(message)
/* END FORBIDDEN EXCEPTIONS */

/* UNAUTHORIZED EXCEPTION */
open class UnauthorizedException (override var message: String): Exception(message)
/* END UNAUTHORIZED EXCEPTION */


/* RUNTIME EXCEPTIONS */
class StorageException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}