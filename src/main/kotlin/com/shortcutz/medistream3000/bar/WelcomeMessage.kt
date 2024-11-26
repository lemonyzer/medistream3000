package com.shortcutz.medistream3000.bar

import org.springframework.stereotype.Component

@Component
class WelcomeMessage {

    fun getWelcomeMessage() {
        System.out.println("Welcome the game started.")
    }

}