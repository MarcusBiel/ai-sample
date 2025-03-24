package com.example.rest.dto;

/**
 * DTO for performing a search query on documents.
 */
public record SearchRequestDto(String query, Integer maxSearchResults) {

    /**
     * Static factory method for creating a search request.
     *
     * @param query            The text query to search for within documents.
     * @param maxSearchResults The maximum number of search results to return.
     * @return A new instance of SearchRequestDto.
     */
    public static SearchRequestDto of(String query, Integer maxSearchResults) {
        return new SearchRequestDto(query, maxSearchResults);
    }
}
