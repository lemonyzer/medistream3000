package com.shortcutz.medistream3000.htmx

import com.shortcutz.medistream3000.data.Appointment
import com.shortcutz.medistream3000.data.AppointmentRepository
import com.shortcutz.medistream3000.data.PatientEntityRepository
import com.shortcutz.medistream3000.data.RoomEntityRepository
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
class AppointmentControllerHtmx (
    var appointmentRepository: AppointmentRepository,
    var roomEntityRepository: RoomEntityRepository, private val patientEntityRepository: PatientEntityRepository
) {

    // Tutorial: https://github.com/KindsonTheGenius/fleetappv1/tree/master/src/main/resources/static
    // https://www.kindsonthegenius.com/spring-boot/build-a-complete-spring-boot-application-from-the-scratch-step-by-step/
    // Hey everyone, please find the completed application and files below
    //Complete Application on zip file – https://drive.google.com/file/d/19Kf-ZvVqSAeN8OJIMArkELQW6vDlTO6g/view?usp=sharing
    //This is the git repository https://github.com/KindsonTheGenius/fleetmsv1.git

    //Get All Appointments
    @GetMapping("appointments")
    fun findAll(model: Model): String {

        val staticLogger = KotlinLogging.logger {}
        staticLogger.info("${this.javaClass} - delete")

        model.addAttribute("appointments", appointmentRepository.findAll())
        model.addAttribute("rooms", roomEntityRepository.findAll())
        model.addAttribute("patienten", patientEntityRepository.findAll())
        return "appointment"
    }

    @RequestMapping("appointments/findById")
    @ResponseBody
    fun findById(id: Long): Optional<Appointment> {
        return appointmentRepository.findById(id)
    }

    //Add VehicleHire
    @PostMapping(value = ["appointments/addNew"])
    fun addNew(appointment: Appointment): String {
        appointmentRepository.save(appointment)
        return "redirect:/appointments"
    }

    @RequestMapping(value = ["appointments/update"], method = [RequestMethod.PUT, RequestMethod.GET])
    fun update(appointment: Appointment): String {
        appointmentRepository.save(appointment)
        return "redirect:/appointments"
    }

    @RequestMapping(value = ["appointments/delete"], method = [RequestMethod.DELETE, RequestMethod.GET])
    fun delete(id: Long): String {
        var a = appointmentRepository.findById(id)

        val staticLogger = KotlinLogging.logger {}
        staticLogger.info("${this.javaClass} - delete")

        if(a.isPresent)
            appointmentRepository.delete(a.get())
        return "redirect:/appointments"
    }
}