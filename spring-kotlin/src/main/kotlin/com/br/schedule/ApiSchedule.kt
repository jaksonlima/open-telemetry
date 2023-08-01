package com.br.schedule

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.*
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@EnableScheduling
@Configuration
class ApiSchedule {

    @Value("\${API_CLIENT}")
    lateinit var apiClient: String

    private val logger = LoggerFactory.getLogger(ApiSchedule::class.java)
    private var restTemplate: RestTemplate = RestTemplate()
    private var objectMapper: ObjectMapper = ObjectMapper()

    @Scheduled(fixedDelay = 5000)
    @Throws(JsonProcessingException::class)
    fun callApiSchedule() {
        val aBuildUrl = UriComponentsBuilder.fromHttpUrl(apiClient)
            .path("/java/rolldice")
            .queryParam("player", "KOTLIN-" + UUID.randomUUID())
            .encode()
            .toUriString()

        val headers = HttpHeaders()
        headers[HttpHeaders.ACCEPT] = MediaType.APPLICATION_JSON_VALUE
        val entity = HttpEntity<Any>(headers)

        val aResponse = restTemplate.exchange(
            aBuildUrl,
            HttpMethod.GET,
            entity,
            Object::class.java
        )

        logger.info("API KOTLIN RESPONSE: {}", objectMapper.writeValueAsString(aResponse))
    }


}