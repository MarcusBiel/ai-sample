package com.example.domain;

import java.util.List;

public record EmbeddingRequest(String model, List<String> input) {

    public static EmbeddingRequest of(String model, String input) {
        return new EmbeddingRequest(model, List.of(input));
    }
}
