package com.shortcutz.medistream3000.htmx

import com.shortcutz.medistream3000.data.Appointment
import com.shortcutz.medistream3000.data.AppointmentRepository
import com.shortcutz.medistream3000.data.RoomEntity
import com.shortcutz.medistream3000.data.RoomEntityRepository
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
class RoomControllerHtmx (
    var roomEntityRepository: RoomEntityRepository
) {

    // Tutorial: https://github.com/KindsonTheGenius/fleetappv1/tree/master/src/main/resources/static
    // https://www.kindsonthegenius.com/spring-boot/build-a-complete-spring-boot-application-from-the-scratch-step-by-step/
    // Hey everyone, please find the completed application and files below
    //Complete Application on zip file – https://drive.google.com/file/d/19Kf-ZvVqSAeN8OJIMArkELQW6vDlTO6g/view?usp=sharing
    //This is the git repository https://github.com/KindsonTheGenius/fleetmsv1.git

    //Get All Appointments
    @GetMapping("rooms")
    fun findAll(model: Model): String {
        model.addAttribute("rooms", roomEntityRepository.findAll())

        return "appointment"
    }

    @RequestMapping("rooms/findById")
    @ResponseBody
    fun findById(uuid: UUID): RoomEntity? {
        return roomEntityRepository.findByRoomUuid(uuid)
    }

    //Add VehicleHire
    @PostMapping(value = ["rooms/addNew"])
    fun addNew(room: RoomEntity): String {
        roomEntityRepository.save(room)
        return "redirect:/rooms"
    }

    @RequestMapping(value = ["rooms/update"], method = [RequestMethod.PUT, RequestMethod.GET])
    fun update(room: RoomEntity): String {
        roomEntityRepository.save(room)
        return "redirect:/rooms"
    }

    @RequestMapping(value = ["rooms/delete"], method = [RequestMethod.DELETE, RequestMethod.GET])
    fun delete(uuid: UUID): String {
        var a = roomEntityRepository.findById(uuid)

        val staticLogger = KotlinLogging.logger {}
        staticLogger.info("${this.javaClass} - delete")

        if(a.isPresent)
            roomEntityRepository.delete(a.get())
        return "redirect:/rooms"
    }
}