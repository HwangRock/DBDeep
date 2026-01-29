package org.example.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.presentation.GeminiRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GeminiClient implements LlmClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GeminiClient(ObjectMapper objectMapper, HttpClient httpClient) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
    }

    @Value("${gemini.api.key}")
    private String key;

    @Value("${gemini.model}")
    private String model;

    @Override
    public String chat(String prompt) throws JsonProcessingException {

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                        + model
                        + ":generateContent?key="
                        + key;

        GeminiRequest body = new GeminiRequest(prompt);
        String json = objectMapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();


        try {

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String answer = extractAnswer(response.body());
            System.out.println(answer);
            return response.body();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String extractAnswer(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);

            return root
                    .path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }
}
