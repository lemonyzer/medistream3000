package com.shortcutz.medistream3000.htmx

import com.shortcutz.medistream3000.data.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
class PatientControllerHtmx (
    var patientEntityRepository: PatientEntityRepository
) {

    // Tutorial: https://github.com/KindsonTheGenius/fleetappv1/tree/master/src/main/resources/static
    // https://www.kindsonthegenius.com/spring-boot/build-a-complete-spring-boot-application-from-the-scratch-step-by-step/
    // Hey everyone, please find the completed application and files below
    //Complete Application on zip file – https://drive.google.com/file/d/19Kf-ZvVqSAeN8OJIMArkELQW6vDlTO6g/view?usp=sharing
    //This is the git repository https://github.com/KindsonTheGenius/fleetmsv1.git

    //Get All Appointments
    @GetMapping("patienten")
    fun findAll(model: Model): String {
        model.addAttribute("patienten", patientEntityRepository.findAll())

        return "appointment"
    }

    @RequestMapping("patienten/findById")
    @ResponseBody
    fun findById(uuid: UUID): PatientEntity? {
        return patientEntityRepository.findByPatientUuid(uuid)
    }

    //Add VehicleHire
    @PostMapping(value = ["patienten/addNew"])
    fun addNew(patientEntity: PatientEntity): String {
        patientEntityRepository.save(patientEntity)
        return "redirect:/patienten"
    }

    @RequestMapping(value = ["patienten/update"], method = [RequestMethod.PUT, RequestMethod.GET])
    fun update(patientEntity: PatientEntity): String {
        patientEntityRepository.save(patientEntity)
        return "redirect:/patienten"
    }

    @RequestMapping(value = ["patienten/delete"], method = [RequestMethod.DELETE, RequestMethod.GET])
    fun delete(uuid: UUID): String {
        var a = patientEntityRepository.findById(uuid)
        if(a.isPresent)
            patientEntityRepository.delete(a.get())
        return "redirect:/patienten"
    }
}