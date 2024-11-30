package com.shortcutz.medistream3000.data

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class StudentController {
    @RequestMapping(value = ["/saveStudent"], method = [RequestMethod.POST])
    fun saveStudent(model: Model, @ModelAttribute("student") student: Student?): String {
        model.addAttribute("student", student)
        return "datePicker/displayDate.html"
    }
}