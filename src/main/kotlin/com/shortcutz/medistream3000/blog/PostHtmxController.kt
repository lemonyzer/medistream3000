package com.shortcutz.medistream3000.blog

import com.shortcutz.medistream3000.data.Appointment
import com.shortcutz.medistream3000.data.AppointmentExt
import com.shortcutz.medistream3000.data.AppointmentRepository
import com.shortcutz.medistream3000.data.CalenderDayData
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.absoluteValue


data class CalenderDay(
    val isToday: Boolean,
    val label: String
)

@Controller
@RequestMapping("/htmx/posts")
class PostHtmxController (
    val postService: PostService,
    val appointmentRepository: AppointmentRepository
) {

    @GetMapping
    fun getAllPosts(model: Model): String {
        val posts = postService.getAllPosts()

        val staticLogger = KotlinLogging.logger {}
        staticLogger.info("getAllPosts size: ${posts.size}")

        val now = LocalDate.now()
        val monday = now.with(ChronoField.DAY_OF_WEEK, 1).atTime(0, 0, 0, 0)
        val tuesday = now.with(ChronoField.DAY_OF_WEEK, 1).atTime(0, 0, 0, 0)


        val today = LocalDate.now()
        val todayDayNameAbbr = today.dayOfWeek.name.subSequence(0,3).toString()
        val todayDayOfMonth = today.dayOfMonth.absoluteValue.toString()

        val todayMonthName = today.month.name
        val todayYear = today.year
        val todayDayLabel = "${todayDayNameAbbr} ${todayDayOfMonth}. ${todayMonthName.subSequence(0,3)} $todayYear"

        val amountOfDays = 6L   // TODO: check why this i amountOfDays

        val ld = LocalDate.now()
        val dayOfMonth = ld.dayOfMonth
        val mondayDayOfMonth = monday.dayOfMonth
        val month = monday.month

        val appointmentList = appointmentRepository.findTop3ByStartTimestampGreaterThanEqual(Instant.now())
//        val appointmentsList = appointmentRepository.findByStartTimestampBetween(
//            Instant.now().truncatedTo(ChronoUnit.DAYS),
//            Instant.now().truncatedTo(ChronoUnit.DAYS).plus(amountOfDays,ChronoUnit.DAYS))

/*
        val appointmentEntityListNextXDays = arrayListOf<Objects>()
        appointmentRepository.getAllAppointmentsAkaNextXDaysScheduledByDay(//findByStartTimestampBetweenOrderByStartTimestampAsc
            Instant.now().truncatedTo(ChronoUnit.DAYS).toString(),
            Instant.now().truncatedTo(ChronoUnit.DAYS).plus(amountOfDays,ChronoUnit.DAYS).toString())?.forEach(appointmentEntityListNextXDays::add)

        staticLogger.info("appointments findByStartTimestampBetween size: ${appointmentList!!.size}")
        staticLogger.info { "appointmentEntityListNextXDays today + $amountOfDays  = [${appointmentEntityListNextXDays.size}] records" }

        //val daySchedule = appointmentList.findNextXDays()
*/

        val xAmountOfDays = 1L

        val startTs = Instant.now()//.truncatedTo(ChronoUnit.DAYS)

        val res = appointmentRepository.findAllByStartTimestampBetweenOrderByStartTimestampAsc(
            Instant.now().truncatedTo(ChronoUnit.DAYS),
            Instant.now().truncatedTo(ChronoUnit.DAYS).plus(amountOfDays,ChronoUnit.DAYS))

        val top3 = appointmentRepository.findTop3ByStartTimestampBetweenOrderByStartTimestampAsc(Instant.now().truncatedTo(ChronoUnit.DAYS),
            Instant.now().truncatedTo(ChronoUnit.DAYS).plus(amountOfDays,ChronoUnit.DAYS))

        val startTsFrom7 = Instant.now().truncatedTo(ChronoUnit.DAYS).plus(7L,ChronoUnit.HOURS)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault())
        val formattedStartDateTime = formatter.format(startTsFrom7)
        val formattedFinishDateTime = formatter.format(startTsFrom7.plus(25L,ChronoUnit.MINUTES))

        var startTimeFrom7 = Date.from(LocalDateTime.parse(formattedStartDateTime, formatter).toInstant(ZoneOffset.ofHours(1)))
        var finishTime = Date.from(LocalDateTime.parse(formattedFinishDateTime, formatter).toInstant(ZoneOffset.ofHours(1)))

        staticLogger.info { " appointmentRepository " +
            "findAll between ${Instant.now().truncatedTo(ChronoUnit.DAYS)} and ${Instant.now().truncatedTo(ChronoUnit.DAYS).plus(amountOfDays,ChronoUnit.DAYS)} " +
            " and " +
            "appointmentStartTime between [startTime=$startTimeFrom7,finishTime=$finishTime]" }


        // appointmentsWithSameTimeSlotDifferentDays
        // loop through timeSlots NOT days!
        val amountOfTimeSlotsPerDay = 5
        val calenderRows = ArrayList<List<Appointment>>()
        val listOfExtAppointment = ArrayList<AppointmentExt>()
        for (i in 1..amountOfTimeSlotsPerDay)
        {

            val iStartTime = Date(startTimeFrom7.time.plus(30L*60L*1000L*i.toLong()).minus(1L))
            val iFinishTime = Date(finishTime.time.plus(30L*60L*1000L*i.toLong()).plus(1L))

            //val appointmentsExt = AppointmentExt(listOf<Appointment>(), Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS).plus(7L,ChronoUnit.HOURS)))
            val appointmentsExt = AppointmentExt(listOf<Appointment>(), Date(iStartTime.time.plus(1L)))


            staticLogger.warn { """i=$i 
                 appointmentRepository 
                 findAll between 
                 ${Instant.now().truncatedTo(ChronoUnit.DAYS)} and 
                 ${Instant.now().truncatedTo(ChronoUnit.DAYS).plus(amountOfDays,ChronoUnit.DAYS)} 
                    and 
                 appointmentStartTime between 
                    iStartTime=$iStartTime and
                    iFinishTime=$iFinishTime]""" }

            val calenderRowAppointments = appointmentRepository.findAllByStartTimestampBetweenAndStartTimeBetweenOrderByStartTimestampAsc(
                Instant.now().truncatedTo(ChronoUnit.DAYS),
                Instant.now().truncatedTo(ChronoUnit.DAYS).plus(amountOfDays,ChronoUnit.DAYS),
                iStartTime,
                iFinishTime
            )

            if (calenderRowAppointments != null) {
                staticLogger.info { " calenderRows  has [${calenderRowAppointments.size}] appointment-records" }
                for(appointment in calenderRowAppointments) {
                    staticLogger.info { "appointment.startTime = ${appointment.startTime}" }
                    staticLogger.info { "appointment.appointmentDate = ${appointment.appointmentDate}" }
                    staticLogger.info { "appointment.id = ${appointment.id}" }
                    staticLogger.info { "appointment.medistreamId = ${appointment.medistreamId}" }
                    staticLogger.info { "appointment.booked = ${appointment.booked}" }
                    staticLogger.info { "appointment.duration = ${appointment.duration}" }
                }

                appointmentsExt.appointments = calenderRowAppointments.toMutableList()
                listOfExtAppointment.add(appointmentsExt)
                calenderRows.add(calenderRowAppointments)
            }
        }

        staticLogger.info { "calenderRows size = ${calenderRows.size}" }

        for(row in calenderRows) {
            staticLogger.info { "row size = ${row.size}" }
            for(appointment in row) {
                staticLogger.info { "appointment.startTime = ${appointment.startTime}" }
                staticLogger.info { "appointment.appointmentDate = ${appointment.appointmentDate}" }
                staticLogger.info { "appointment.id = ${appointment.id}" }
                staticLogger.info { "appointment.medistreamId = ${appointment.medistreamId}" }
            }
        }



        if (res != null) {
            staticLogger.info { "res xAmountOfDays = + $xAmountOfDays  = [${res.size}] records" }
        }

        // AI: kotlin to sql
        //     kotlin to java
        //     kotlin to unity
        //     kotlin to html
        //      kotlin to js /
        //      UNITIYCODE
        // WO VERARBEITE ICH DATEN FÜR DIE AUSGABE? kotlin - postgrsql - thymeleaf - htmx - (web)
        for (appointment in res!!) {
            staticLogger.info { "res next X=$xAmountOfDays days " + appointment.appointmentDate }
            staticLogger.info { "res dayLabel " + appointment.dayLabel }
        }



        //appointmentRepository.findAll().forEach(appointmentsList::add)

        //staticLogger.info("appointments +findAll size: ${appointmentsList.size}")

        val tomorrow = today.plusDays(1)
        val nextDayOfWeek = tomorrow.dayOfWeek

        println("Tomorrow is ${nextDayOfWeek.name}")

        val calendarDayList = ArrayList<CalenderDay>()
        for(i in 0L..5L) {
            val dayName = "${today.plusDays(i).dayOfWeek.name}"

            val dayNameAbbr = today.plusDays(i).dayOfWeek.name.subSequence(0,3).toString()
            val dayOfMonth = today.plusDays(i).dayOfMonth.absoluteValue.toString()

            val monthName = today.plusDays(i).month.name
            val dayLabel = "${dayNameAbbr} ${dayOfMonth}. ${monthName.subSequence(0,3)}"
            calendarDayList.add(CalenderDay(if (i == 0L) true else false, dayLabel))
        }

        // calendar day
        //      -- name
        //      -- appointments

        // days of select
        val listOfWeekDays = ArrayList<String>()
//        res.forEach(listOfWeekDays::add)
        for(appointments in res) {
            listOfWeekDays.add(appointments.dayName)
        }

        val listOfCalenderDayData = ArrayList<CalenderDayData>()
        var found : Boolean
        for(appointments in res) {
            // store appointments distinct (dayName, weekDay)
            found = false;
            for(data in listOfCalenderDayData) {
                if(appointments.dayName === data.weekDay &&
                    appointments.dayOfMonth === data.dayOfMonth) {
                    found = true;
                    break;
                }
            }

            if(found) {
                continue
            } else {
                listOfCalenderDayData.add( CalenderDayData(0,appointments.dayName,appointments.dayOfMonth))
            }
        }

        for(calenderDayData in listOfCalenderDayData) {
            staticLogger.info("calenderDayData : ${calenderDayData.dayOfMonth} - ${calenderDayData.weekDay} ")
        }

        // Data:
        // Monday 7:00 - 7:30
        // Tuesday 7:00 - 7:30
        // Wednesday 7:00 - 7:30



        model.addAttribute("title", "Blog Page")
        model.addAttribute("content", "all-posts")
        model.addAttribute("content2", "all-appointments")
        model.addAttribute("content3", "next-x-days-calendar")

        model.addAttribute("posts", posts)
        model.addAttribute("currentDay", Instant.now().truncatedTo(ChronoUnit.DAYS))
        model.addAttribute("amountOfDays", amountOfDays)
        model.addAttribute("dayOfMonth", dayOfMonth)
        model.addAttribute("mondayDayOfMonth", mondayDayOfMonth)
        model.addAttribute("month", month)
        model.addAttribute("monday", monday.truncatedTo(ChronoUnit.DAYS))
        model.addAttribute("appointments", appointmentList)
        model.addAttribute("listOfWeekDays", listOfWeekDays.distinct())

        model.addAttribute("todayDayLabel", todayDayLabel)

        model.addAttribute("calendarDayList", calendarDayList)
        model.addAttribute("appointmentsSchedule", res)
        model.addAttribute("top3appointmentsSchedule", top3)
        model.addAttribute("listOfCalenderDayData", listOfCalenderDayData)
        model.addAttribute("calenderRows", calenderRows)
        model.addAttribute("listOfExtAppointment", listOfExtAppointment)
        //model.addAttribute("appointmentEntityListNextXDaysDaySchedule", appointmentEntityListNextXDays)


//        val calendar = Calendar.getInstance()
//        staticLogger.info( calendar.firstDayOfWeek.toString())
//        staticLogger.info( calendar.)
        return "index"
    }

    @PostMapping
    fun createPost(@RequestParam text: String?, model: Model): String {
        val post = Post()
        post.text=text
        postService.createPost(post)
        val posts = postService.getAllPosts()
        model.addAttribute("posts", posts)
        return "blog/all-posts :: #posts-list"
    }

    @PostMapping("/appointment")
    fun createAppointment(@RequestParam time: String?, model: Model): String {
        val appointment = Appointment(
            0,
            appointmentUuid = UUID.randomUUID(),
            appointmentDate = Date.from(Instant.now()),
            dayLabel = "String",
            dayName = "String",
            dayNameAbbreviator = "String",
            monthName = "String",
            dayOfMonth = "String",
            duration = 25L,
            medistreamId = 0L,
            booked = 0,
            startTimestamp = Instant.now(),
            startTime = Date.from(Instant.now()),
            finishTime = Date.from(Instant.now().plus(25L,ChronoUnit.MINUTES)),
        )


        appointmentRepository.save(appointment)
        val appointments = appointmentRepository.findAll()
        model.addAttribute("appointments", appointments)
        return "blog/all-appointments :: #appointments-list"
    }

    @GetMapping("/{id}/edit")
    fun getPostForEdit(@PathVariable id: Long?, model: Model): String {
        val post = postService.getPostById(id)
        model.addAttribute("post", post)
        return "blog/edit-post-form :: #edit-form-container"
    }

    @GetMapping("/appointment/{id}/edit")
    fun getAppointmentForEdit(@PathVariable id: Long?, model: Model): String {
        val appointment = appointmentRepository.findById(id!!)
        model.addAttribute("appointment", appointment.get())    // : Appointment not : Optional!
        return "blog/edit-appointment-form :: #edit-form-container"
    }

    @PatchMapping("/{id}")
    fun updatePost(@PathVariable id: Long?, @RequestParam text: String?, model: Model): String {
        val post = postService.getPostById(id)
        post.text=text
        postService.updatePost(id, post)
        val posts = postService.getAllPosts()
        model.addAttribute("posts", posts)
        return "blog/all-posts :: #posts-list"
    }

    @PatchMapping("/appointment/{id}")
    fun updateAppointment(@PathVariable id: Long?,
                          @RequestParam medistreamId: Long?,
                          @RequestParam startTimestamp: String?,
                          @RequestParam starTime: String?,
                          @RequestParam finishTime: String?,
                          @RequestParam duration: Long?,
                          @RequestParam booked: Int?,
                          model: Model): String {
        val appointment = id?.let { appointmentRepository.findById(id) }
        if (appointment != null) {

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneOffset.UTC)
            val dateTime = LocalDateTime.parse(startTimestamp!!, formatter)

            appointment.get().startTimestamp = dateTime.toInstant(ZoneOffset.UTC)
            appointment.get().medistreamId = medistreamId!!
            appointment.get().duration = duration!!
            appointment.get().booked = booked!!
        }
        appointmentRepository.save(appointment!!.get())
        val appointments = appointmentRepository.findAll()
        model.addAttribute("appointments", appointments)
        return "blog/all-appointments :: #appointments-list"
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long?, model: Model): String {
        postService.deletePost(id)
        val posts = postService.getAllPosts()
        model.addAttribute("posts", posts)
        return "blog/all-posts :: #posts-list"
    }

    @DeleteMapping("/appointment/{id}")
    fun deleteAppointment(@PathVariable id: Long?, model: Model): String {
        if (id != null) {
            appointmentRepository.deleteById(id)
        }
        val appointments = appointmentRepository.findAll()
        model.addAttribute("appointments", appointments)
        return "blog/all-appointments :: #appointments-list"
    }
}