package com.shortcutz.medistream3000.htmx

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import java.sql.Date
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime



@Controller
class HtmxController {

    @RequestMapping("")
    fun getAppointments(model : Model) : String {
        model.addAttribute("heading", "Users & Appointments")
        model.addAttribute("name", "John Doe")
        model.addAttribute("age", 25)
//        model.addAttribute("standardDate", Date())
//        model.addAttribute("localDateTime", LocalDateTime.now())
//        model.addAttribute("localDate", LocalDate.now())
//        model.addAttribute("timestamp", Instant.now())
        return "userView"
    }

//    @RequestMapping("")
//    fun getAppointments(model : Model) : String {
//        model.addAttribute("heading", "Appointments")
////        model.addAttribute("medistreamId", "Users")
////        model.addAttribute("date", Date.valueOf(""))
//        //model.addAttribute("name", "John Doe")
//        //model.addAttribute("age", 25)
//        return "userView"
//    }
}