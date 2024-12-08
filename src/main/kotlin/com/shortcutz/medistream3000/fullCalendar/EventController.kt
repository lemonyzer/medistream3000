package com.shortcutz.medistream3000.fullCalendar

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId


@RestController
@RequestMapping("/calendar")
internal class EventController (
    val eventRepository: EventJpaRepository
) {

    @RequestMapping(value = ["/allevents"], method = [RequestMethod.GET])
    fun allEvents(): List<Event?> {
        return eventRepository.findAll()
    }

    @RequestMapping(value = ["/event"], method = [RequestMethod.POST])
    fun addEvent(@RequestBody event: Event?): Event {
        val created = eventRepository.save<Event>(event!!)
        return created
    }

    @RequestMapping(value = ["/event"], method = [RequestMethod.PATCH])
    fun updateEvent(@RequestBody event: Event?): Event {
        return eventRepository.save<Event>(event!!)
    }

    @RequestMapping(value = ["/event"], method = [RequestMethod.DELETE])
    fun removeEvent(@RequestBody event: Event?) {
        eventRepository.delete(event!!)
    }

    @RequestMapping(value = ["/events"], method = [RequestMethod.GET])
    fun getEventsInRange(
        @RequestParam(value = "start", required = true) start: String,
        @RequestParam(value = "end", required = true) end: String
    ): List<Event?>? {
        var startDate: java.util.Date? = null
        var endDate: java.util.Date? = null
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd")

        try {
            startDate = inputDateFormat.parse(start)
        } catch (e:  java.text.ParseException) {
            throw BadDateFormatException("bad start date: $start")
        }

        try {
            endDate = inputDateFormat.parse(end)
        } catch (e: java.text.ParseException) {
            throw BadDateFormatException("bad end date: $end")
        }

        val startDateTime = LocalDateTime.ofInstant(
            startDate.toInstant(),
            ZoneId.systemDefault()
        )

        val endDateTime = LocalDateTime.ofInstant(
            endDate.toInstant(),
            ZoneId.systemDefault()
        )

        return eventRepository.findByDateBetween(startDateTime, endDateTime)
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
internal class BadDateFormatException(dateString: String?) : RuntimeException(dateString) {
    companion object {
        private const val serialVersionUID = 1L
    }
}