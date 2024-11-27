package com.shortcutz.medistream3000.htmx

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HtmxController {

    @RequestMapping("")
    fun getAppointments(model : Model) : String {
        model.addAttribute("heading", "Users")
        model.addAttribute("name", "John Doe")
        model.addAttribute("age", 25)
        return "userView"
    }
}