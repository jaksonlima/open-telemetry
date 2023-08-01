package com.br.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@EnableScheduling
@Configuration
public class ApiSchedule {

    @Value("${API_CLIENT}")
    private String apiClient;

    private static final Logger logger = LoggerFactory.getLogger(ApiSchedule.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedDelay = 5000)
    public void callApiSchedule() throws JsonProcessingException {
        final var aBuildUrl = UriComponentsBuilder.fromHttpUrl(apiClient)
                .path("/kotlin/rolldice")
                .queryParam("player", "JAVA-" + UUID.randomUUID())
                .encode()
                .toUriString();

        final var headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final var entity = new HttpEntity<>(headers);

        final var aResponse = restTemplate.exchange(
                aBuildUrl,
                HttpMethod.GET,
                entity,
                Object.class
        );

        logger.info("API JAVA RESPONSE: {}", objectMapper.writeValueAsString(aResponse));
    }

}
