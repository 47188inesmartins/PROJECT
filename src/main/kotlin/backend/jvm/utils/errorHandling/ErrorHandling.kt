package backend.jvm.utils.errorHandling

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
internal class ErrorHandling {
    @ExceptionHandler(value = [Exception::class])
    @Throws(Exception::class)
    fun defaultErrorHandler(req: HttpServletRequest, e: Exception): ModelAndView {
        if (AnnotationUtils.findAnnotation(e.javaClass, ResponseStatus::class.java) != null) throw e

        val mav = ModelAndView()
        mav.addObject("exception", e)
        mav.addObject("url", req.requestURL)
        mav.viewName = DEFAULT_ERROR_VIEW
        return mav
    }

    companion object {
        const val DEFAULT_ERROR_VIEW = "error"
    }
}