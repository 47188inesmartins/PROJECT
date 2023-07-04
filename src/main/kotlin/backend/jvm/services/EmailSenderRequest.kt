package backend.jvm.services

data class EmailSenderRequest(
    val to: String,
    val subject: String,
    val text: String
)