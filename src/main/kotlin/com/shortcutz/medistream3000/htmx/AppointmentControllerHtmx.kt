package com.shortcutz.medistream3000.htmx

import com.shortcutz.medistream3000.data.Appointment
import com.shortcutz.medistream3000.data.AppointmentRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
class AppointmentControllerHtmx (
    var appointmentRepository: AppointmentRepository
) {

    // Tutorial: https://github.com/KindsonTheGenius/fleetappv1/tree/master/src/main/resources/static
    // https://www.kindsonthegenius.com/spring-boot/build-a-complete-spring-boot-application-from-the-scratch-step-by-step/
    // Hey everyone, please find the completed application and files below
    //Complete Application on zip file – https://drive.google.com/file/d/19Kf-ZvVqSAeN8OJIMArkELQW6vDlTO6g/view?usp=sharing
    //This is the git repository https://github.com/KindsonTheGenius/fleetmsv1.git

    //Get All Appointments
    @GetMapping("appointments")
    fun findAll(model: Model): String {
        model.addAttribute("appointments", appointmentRepository.findAll())

        return "appointment"
    }

    @RequestMapping("appointment/findById")
    @ResponseBody
    fun findById(id: Long): Optional<Appointment> {
        return appointmentRepository.findById(id)
    }

    //Add VehicleHire
    @PostMapping(value = ["appointment/addNew"])
    fun addNew(appointment: Appointment): String {
        appointmentRepository.save(appointment)
        return "redirect:/appointment"
    }

    @RequestMapping(value = ["appointment/update"], method = [RequestMethod.PUT, RequestMethod.GET])
    fun update(appointment: Appointment): String {
        appointmentRepository.save(appointment)
        return "redirect:/appointment"
    }

    @RequestMapping(value = ["appointment/delete"], method = [RequestMethod.DELETE, RequestMethod.GET])
    fun delete(id: Long): String {
        var a = appointmentRepository.findById(id)
        if(a.isPresent)
            appointmentRepository.delete(a.get())
        return "redirect:/appointment"
    }
}