package kz.edu.sdu.regsystem.stand.model.exceptions

/* BAD REQUEST EXCEPTIONS */
open class BadRequestException (override var message: String): Exception(message)

open class UserDoesNotExistsException (override var message: String): BadRequestException(message)

open class UserAlreadyExistsException (override var message: String): BadRequestException(message)
/* END BAD REQUEST EXCEPTIONS */

/* FORBIDDEN EXCEPTIONS */
open class ForbiddenException (override var message: String): Exception(message)

open class UserNotActiveException (override var message: String): ForbiddenException(message)
/* END FORBIDDEN EXCEPTIONS */