package backend.jvm.utils.emails

class EmailMessages {
    companion object{
        fun ValidateAccount(email: String) = "<html><body>" +
        "<h1>Validate your account</h1>" +
        "<p>Hello! This email has been registered in ScheduleIt!</p>" +
        "<p>To verify your account, click on the link below:</p>" +
        "<a href=http://localhost:8000/validate?"+ email +"/>VERIFY</a>" +
        "</body></html>";
    }


}