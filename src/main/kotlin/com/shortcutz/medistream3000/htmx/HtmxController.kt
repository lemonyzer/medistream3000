package com.shortcutz.medistream3000.htmx

import com.shortcutz.medistream3000.data.Appointment
import com.shortcutz.medistream3000.data.AppointmentController
import com.shortcutz.medistream3000.data.AppointmentRepository
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import java.sql.Date
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


@Controller
class HtmxController (
    var appointmentRepository: AppointmentRepository
) {

    @RequestMapping("")
    fun getUserView(model : Model) : String {
        model.addAttribute("heading", "Users & Appointments")
        model.addAttribute("name", "John Doe")
        model.addAttribute("age", 25)
//        model.addAttribute("standardDate", Date())
//        model.addAttribute("localDateTime", LocalDateTime.now())
//        model.addAttribute("localDate", LocalDate.now())
//        model.addAttribute("timestamp", Instant.now())
        return "userView"
    }

    @RequestMapping("a")
    fun getAppointments(model : Model) : String {

        val staticLogger = KotlinLogging.logger {}
        staticLogger.info("${this.javaClass} - getAppointments")

        val appointmentsList: ArrayList<Appointment> = ArrayList<Appointment>()
        appointmentRepository.findByStartTimestampBetween(
            Instant.now().truncatedTo(ChronoUnit.DAYS),
            Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS))?.forEach(appointmentsList::add)

        staticLogger.info("amount of appointments today: ${appointmentsList.count()}")

        model.addAttribute("appointments", appointmentsList)
        return "appointment"
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