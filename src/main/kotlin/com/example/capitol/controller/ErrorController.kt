package com.example.capitol.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest


class ErrorController : ErrorController {
    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest):String {
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)

        if (status != null) {
            val statusCode = Integer.valueOf(status.toString())
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "Page not found"
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "You're not authorised to view this page"
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "Problem on our end"
            }
        }
        return "error"
    }

    override fun getErrorPath(): String {
        TODO("Not yet implemented")
    }
}