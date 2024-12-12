package com.shortcutz.medistream3000.data

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PatientController(val patientenRepository: PatientEntityRepository) {

    @GetMapping("/hello")
    fun home(): String {
        return "Hello World"
    }

    @GetMapping("/api/patienten")
    fun findAll() : List<PatientEntity> {
        return patientenRepository.findAll()
    }
}