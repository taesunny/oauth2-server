package com.sunny.oauth2.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class AccountController {
    @GetMapping("/")
    fun viewIndex(request: HttpServletRequest): String {
        return "index"
    }

    @GetMapping("/view/success")
    fun viewSuccess(request: HttpServletRequest): String {
        return "success"
    }
}