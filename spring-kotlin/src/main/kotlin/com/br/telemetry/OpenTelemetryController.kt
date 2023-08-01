package com.br.telemetry

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@RestController
class OpenTelemetryController {

    private val logger: Logger = LoggerFactory.getLogger(OpenTelemetryController::class.java)

    @GetMapping("/kotlin/rolldice")
    fun index(@RequestParam("player") player: Optional<String?>): String {
        val result = getRandomNumber(1, 6)
        if (player.isPresent) {
            logger.info("{} is rolling the dice: {}", player.get(), result)
        } else {
            logger.info("Anonymous player is rolling the dice: {}", result)
        }
        return result.toString()
    }

    fun getRandomNumber(min: Int, max: Int): Int {
        return ThreadLocalRandom.current().nextInt(min, max + 1)
    }

}