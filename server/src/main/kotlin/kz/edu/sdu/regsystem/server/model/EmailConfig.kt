package kz.edu.sdu.regsystem.server.model

data class EmailConfig(
    var host: String,
    var port: Int,
    var username: String,
    var password: String,
    var transportProtocol: String,
    var auth: Boolean,
    var starttlsEnabled: Boolean,
    var frontendUrl: String
)