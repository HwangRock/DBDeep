package org.example.llm;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LlmClient {
    String chat(String prompt) throws JsonProcessingException;

    String extractAnswer(String json);
}
