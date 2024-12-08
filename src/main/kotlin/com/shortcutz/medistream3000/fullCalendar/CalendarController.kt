package com.shortcutz.medistream3000.fullCalendar

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView


@Controller
@RequestMapping("/calendar")
internal class CalendarController (
    val eventJpaRepository: EventJpaRepository
) {

    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun index(): ModelAndView {
        return ModelAndView("calendar/index")
    }
    @RequestMapping(value = ["/day"], method = [RequestMethod.GET])
    fun dayGrid(): ModelAndView {
        return ModelAndView("calendar/dayGrid")
    }

    @RequestMapping(value = ["/staticcalendar"], method = [RequestMethod.GET])
    fun staticcalendar(): ModelAndView {
        return ModelAndView("calendar/staticcalendar")
    }

    @RequestMapping(value = ["/calendar"], method = [RequestMethod.GET])
    fun calendar(): ModelAndView {
        return ModelAndView("calendar/calendar")
    }

    @RequestMapping(value = ["/jsoncalendar"], method = [RequestMethod.GET])
    fun jsoncalendar(): ModelAndView {
        return ModelAndView("calendar/jsoncalendar")
    }

    @RequestMapping(value = ["/eventlist"], method = [RequestMethod.GET])
    fun events(request: HttpServletRequest?, model: Model): String {
        model.addAttribute("events", eventJpaRepository.findAll())
        return "calendar/events"
    }
}