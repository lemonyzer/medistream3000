package com.shortcutz.medistream3000.danvega04

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


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
        return "04/modal-new-user :: modal"
    }

    @GetMapping("/editUserModal/{id}")
    fun getEditUser(@PathVariable("id") userId : Int, model: Model): String {

        val user: User = userRepository.getUser(userId)
        model.addAttribute("user", user)
        //return "04/modal-edit-user :: modal"
        return "04/modal-edit-user"
    }

    @PostMapping("/editUserModal")
    fun editUser(user : User, model: Model): String {

        userRepository.updateUser(user.id, user)

        model.addAttribute("user", user)
        //return "04/modal-edit-user :: modal"
        return "redirect:/users"
    }

    @GetMapping("/getFakeUser")
    fun getFakeUser(model: Model): String {
        val user: User = userRepository.createFakeUser()
        model.addAttribute("user", user)
        return "04/new-user-form :: frmNewUser"
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody user: User?, model: Model?) : String {

        model?.addAttribute("users", userRepository.findAll())

        return "04/index :: #user-list"
    }

    @GetMapping("/new")
    fun newUser(model : Model) : String {
        model.addAttribute("formData", UserFormData(
            name = "",
            title = "",
            email = "",
            role = ""
        ))
        return "04/new-user-form :: frmNewUse";
    }

    @PostMapping("/new")
    fun createNewUser(model : Model, @ModelAttribute("formData") formData : UserFormData) : String {

        val usr = User(formData.name,formData.title, formData.email, formData.role)

        userRepository.addUser(usr)

        return "redirect:/users";
    }



    @PostMapping("/save")
    fun save(@RequestParam name: String,
             @RequestParam title: String,
             @RequestParam email: String,
             @RequestParam role: String,
             model: Model?): String {
        // add new user

        //val newUser = userRepository.createFakeUser2()
        val newUser = User(name,title,email,role)
        userRepository.addUser(newUser)

        model?.addAttribute("users", userRepository.findAll())

        return "04/index :: #user-list"
        //return "04/index"
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserController::class.java)
    }
}