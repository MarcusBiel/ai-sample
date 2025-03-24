package com.example.domain;

import java.util.List;

/**
 * Response wrapper for OpenAI embedding API.
 *
 * @param data List of embedding data entries
 */
public record EmbeddingResponse(List<EmbeddingData> data) {

    /**
     * Creates a new EmbeddingResponse instance.
     *
     * @param data list of embedding data entries
     * @return new EmbeddingResponse
     */
    public static EmbeddingResponse of(List<EmbeddingData> data) {
        return new EmbeddingResponse(data);
    }
}
