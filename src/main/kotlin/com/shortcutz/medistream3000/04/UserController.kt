package com.shortcutz.medistream3000.`04`

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/users")
class UserController(val userRepository: UserRepository) {

    @GetMapping("")
    fun list(model: Model): String {
        model.addAttribute("users", userRepository.findAll())
        return "04/index"
    }

    @GetMapping("/addUserModal")
    fun addUser(): String {
        return "04/modal :: modal"
    }

    @GetMapping("/getFakeUser")
    fun getFakeUser(model: Model): String {
        val user: User = userRepository.createFakeUser()
        model.addAttribute("user", user)
        return "04/new-user-form :: frmNewUser"
    }

    @PostMapping("/save")
    fun save(@RequestBody user: User?, model: Model?): String {
        // add new user

        val newUser = userRepository.createFakeUser2()


        model?.addAttribute("users", userRepository.findAll())

        return "04/index :: #user-list"
        //return "04/index"
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserController::class.java)
    }
}