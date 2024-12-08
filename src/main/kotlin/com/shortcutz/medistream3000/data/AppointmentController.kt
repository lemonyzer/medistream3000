package com.shortcutz.medistream3000.data

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


@CrossOrigin(origins = ["http://localhost:8081"])
@RestController
@RequestMapping("/api")
class AppointmentController (
    var appointmentRepository: AppointmentRepository
) {
    // source: https://www.bezkoder.com/spring-boot-jpa-h2-example/
//     fun getAllAppointments(@RequestParam(required = false) appointmentUuid: UUID?,
//                        @RequestParam(required = false) appointmentDate: Date?): ResponseEntity<List<Appointment>?> {
    @GetMapping("/appointments")
    fun getAllAppointments(@RequestParam(required = false) appointmentDate: Date?) : ResponseEntity<List<Appointment>?> {
        try {
            val appointmentsList: ArrayList<Appointment> = ArrayList<Appointment>()

            if (appointmentDate == null) appointmentRepository.findAll().forEach(appointmentsList::add)
            else appointmentRepository.findAllByAppointmentDate(appointmentDate)?.forEach(appointmentsList::add)

            if (appointmentsList.isEmpty()) {
                return ResponseEntity<List<Appointment>?>(HttpStatus.NO_CONTENT)
            }

            return ResponseEntity<List<Appointment>?>(appointmentsList, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity<List<Appointment>?>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/today-appointments")
    fun getAllTodayAppointments() : ResponseEntity<List<Appointment>?> {
        try {
            val appointmentsList: ArrayList<Appointment> = ArrayList<Appointment>()

            appointmentRepository.findByStartTimestampBetweenOrderByStartTimestampAsc(
                Instant.now().truncatedTo(ChronoUnit.DAYS),
                Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1,ChronoUnit.DAYS))?.forEach(appointmentsList::add)

            if (appointmentsList.isEmpty()) {
                return ResponseEntity<List<Appointment>?>(HttpStatus.NO_CONTENT)
            }

            return ResponseEntity<List<Appointment>?>(appointmentsList, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity<List<Appointment>?>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/week-appointments")
    fun getAllWeekAppointments(@RequestParam(required = false) weekDay: Instant?) : ResponseEntity<List<Appointment>?> {
        try {
            val appointmentsList: ArrayList<Appointment> = ArrayList<Appointment>()

            if (weekDay == null) appointmentRepository.findAll().forEach(appointmentsList::add)
            else appointmentRepository.findByStartTimestampGreaterThanEqual(weekDay.truncatedTo(ChronoUnit.DAYS))?.forEach(appointmentsList::add)
//            else appointmentRepository.findByStartTimestampAfter(weekDay.truncatedTo(ChronoUnit.DAYS))?.forEach(appointmentsList::add)

            if (appointmentsList.isEmpty()) {
                return ResponseEntity<List<Appointment>?>(HttpStatus.NO_CONTENT)
            }

            return ResponseEntity<List<Appointment>?>(appointmentsList, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity<List<Appointment>?>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

//    @RequestMapping(value = ["/saveAppointment"], method = [RequestMethod.POST])
//    fun saveAppointment(model: Model, @ModelAttribute("appointment") appointment: Appointment?): String {
//        model.addAttribute("appointment", appointment)
//        return "datePicker/displayDate.html"//"datePicker/displayDate.html"
//    }

    @GetMapping("/appointments/{id}")
    fun getAppointmentById(@PathVariable("id") id: Long): ResponseEntity<Appointment> {
        var appointmentData = appointmentRepository.findById(id);

        if (appointmentData.isPresent()) {
            return ResponseEntity<Appointment>(appointmentData.get(), HttpStatus.OK);
        } else {
            return ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/appointments")
    fun createAppointment(@RequestBody appointment: Appointment): ResponseEntity<Appointment> {
        try {
            var _appointment =
                appointmentRepository.save(
                    Appointment(
                        appointment.id,
                        appointment.appointmentUuid,
                        appointment.appointmentDate,
                        appointment.dayLabel,
                        appointment.dayName,
                        appointment.dayNameAbbreviator,
                        appointment.monthName,
                        appointment.dayOfMonth,
                        appointment.duration,
                        appointment.medistreamId,
                        appointment.booked,
                        appointment.startTimestamp,
                        appointment.startTime,
                        appointment.finishTime));
            return ResponseEntity<Appointment>(_appointment, HttpStatus.CREATED);
        } catch (e: Exception) {
            return ResponseEntity<Appointment>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/appointments/{id}")
    fun updateAppointment(@PathVariable("id") id: Long, @RequestBody appointment: Appointment): ResponseEntity<Appointment> {
        var appointmentData = appointmentRepository.findById(id);

        if (appointmentData.isPresent()) {
            var _appointment = appointmentData.get();
            _appointment.id = appointment.id;
            _appointment.medistreamId = appointment.medistreamId
            _appointment.appointmentUuid = appointment.appointmentUuid
            _appointment.duration = appointment.duration
            _appointment.appointmentDate = appointment.appointmentDate
            return ResponseEntity<Appointment>(appointmentRepository.save(_appointment), HttpStatus.OK);
        } else {
            return ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/appointments/{id}")
    fun deleteAppointment(@PathVariable("id")  id: Long) : ResponseEntity<HttpStatus> {
        try {
            appointmentRepository.deleteById(id);
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } catch (e: Exception) {
            return ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/appointments")
    fun deleteAllAppointments() : ResponseEntity<HttpStatus>{
        try {
            appointmentRepository.deleteAll();
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } catch (e: Exception) {
            return ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/appointments/mediStream0")
    fun findByMediStreamId() :  ResponseEntity<List<Appointment>> {
        try {
            val appointments = appointmentRepository.findByMedistreamId(0);

            if (appointments?.isEmpty() == true) {
                return  ResponseEntity<List<Appointment>>(HttpStatus.NO_CONTENT);
            }
            return  ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
        } catch ( e: Exception) {
            return  ResponseEntity<List<Appointment>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}