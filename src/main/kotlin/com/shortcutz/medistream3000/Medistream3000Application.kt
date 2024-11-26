package com.shortcutz.medistream3000

import com.shortcutz.medistream3000.bar.WelcomeMessage
import com.shortcutz.medistream3000.data.PatientEntity
import com.shortcutz.medistream3000.data.PatientEntityRepository
import com.shortcutz.medistream3000.data.RoomEntity
import com.shortcutz.medistream3000.data.RoomEntityRepository
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import java.util.*

@SpringBootApplication
class Medistream3000Application

fun main(args: Array<String>) {

	val staticLogger = KotlinLogging.logger {}

	runApplication<Medistream3000Application>(*args)

	staticLogger.info("Application started successfully")

	var ob1 =  WelcomeMessage()
	ob1.getWelcomeMessage()
}


