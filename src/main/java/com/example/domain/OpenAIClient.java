package com.example.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@ApplicationScoped
public class OpenAIClient {

    private static final String EMBEDDINGS_ENDPOINT = "https://api.openai.com/v1/embeddings";

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @ConfigProperty(name = "openai.api.key")
    String openAiApiKey;

    public OpenAIClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Retrieves embedding vectors for the given input text from OpenAI.
     *
     * @param input Text to create an embedding for
     * @return Embedding as a float array
     */
    public float[] getEmbedding(String input) {
        try {
            EmbeddingRequest requestPayload = EmbeddingRequest.of("text-embedding-ada-002", input);
            String requestBody = objectMapper.writeValueAsString(requestPayload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(EMBEDDINGS_ENDPOINT))
                    .header("Authorization", "Bearer " + openAiApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException("OpenAI API error: " + response.body(), response.statusCode());
            }

            // Use the new EmbeddingResponse record here
            EmbeddingResponse embeddingResponse = objectMapper.readValue(response.body(), EmbeddingResponse.class);
            List<Double> vector = embeddingResponse.data().get(0).embedding();

            float[] embedding = new float[vector.size()];
            for (int i = 0; i < vector.size(); i++) {
                embedding[i] = vector.get(i).floatValue();
            }

            return embedding;

        } catch (Exception e) {
            throw new WebApplicationException("Failed to retrieve embedding from OpenAI", e);
        }
    }
}
