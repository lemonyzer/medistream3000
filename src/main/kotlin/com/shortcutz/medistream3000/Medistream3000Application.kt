package com.shortcutz.medistream3000

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Medistream3000Application

fun main(args: Array<String>) {

	val staticLogger = KotlinLogging.logger {}

	runApplication<Medistream3000Application>(*args)

	staticLogger.info("Application started successfully")
}


