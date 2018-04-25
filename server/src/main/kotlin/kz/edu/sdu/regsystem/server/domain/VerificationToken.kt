package kz.edu.sdu.regsystem.server.domain

import java.util.*

data class VerificationToken(var id: Long,
                             var token: String,
                             var createdDate: Date?,
                             var user: User?)