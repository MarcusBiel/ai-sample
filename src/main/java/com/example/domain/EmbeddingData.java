package com.example.domain;

import java.util.List;

/**
 * Embedding data returned by OpenAI.
 *
 * @param embedding the embedding vector
 */
public record EmbeddingData(List<Double> embedding) {

    /**
     * Creates a new EmbeddingData instance.
     *
     * @param embedding the embedding vector
     * @return new EmbeddingData
     */
    public static EmbeddingData of(List<Double> embedding) {
        return new EmbeddingData(embedding);
    }
}
