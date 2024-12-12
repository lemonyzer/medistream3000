package com.shortcutz.medistream3000.data

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController(val roomRepository: RoomEntityRepository) {

    @GetMapping("/api/rooms")
    fun findAll() : List<RoomEntity> {
        return roomRepository.findAll()
    }
}