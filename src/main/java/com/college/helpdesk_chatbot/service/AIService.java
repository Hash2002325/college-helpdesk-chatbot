package com.college.helpdesk_chatbot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String getAIResponse(String question) {
        try {
            // Prepare request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama3.2:3b");
            requestBody.put("prompt", buildPrompt(question));
            requestBody.put("stream", false);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Call Ollama API
            Map<String, Object> response = restTemplate.postForObject(
                    OLLAMA_URL,
                    request,
                    Map.class
            );

            if (response != null && response.containsKey("response")) {
                return (String) response.get("response");
            }

            return "I'm having trouble connecting to the AI. Please try again.";

        } catch (Exception e) {
            System.err.println("AI Error: " + e.getMessage());
            return "I'm currently unable to process your question. Please contact the admin office.";
        }
    }

    private String buildPrompt(String question) {
        return "You are a helpful college helpdesk assistant. " +
                "Answer the following question briefly and professionally. " +
                "If you don't know the answer, suggest contacting the admin office.\n\n" +
                "Question: " + question + "\n\n" +
                "Answer:";
    }
}
