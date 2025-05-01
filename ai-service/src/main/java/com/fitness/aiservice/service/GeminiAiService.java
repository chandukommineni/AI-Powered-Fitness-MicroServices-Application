package com.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
@Slf4j
@Service
public class GeminiAiService {
    @Value("${gemini.api.url}")
    private String apiURL;
    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public GeminiAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String prompt){
        Map<String,Object> requestBody=Map.of(
                "contents",new Object[]{
                        Map.of(
                                "parts",new Object[]{
                                        Map.of("text", prompt)
                                }
                        )
                }

        );

        try {
            String answer = webClient.post()
                    .uri(apiURL + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return answer;
        }
        catch (Exception e){
            log.info("Failed to Generate Response");
        }
    return "";
    }

}
