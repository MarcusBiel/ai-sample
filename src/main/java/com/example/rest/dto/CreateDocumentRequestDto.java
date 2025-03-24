package com.example.rest.dto;

/**
 * DTO for creating a new document.
 */
public record CreateDocumentRequestDto(String content) {

    /**
     * Static factory method for creating a new document request.
     *
     * @param content The textual content of the new document.
     * @return A new instance of CreateDocumentRequestDto.
     */
    public static CreateDocumentRequestDto of(String content) {
        return new CreateDocumentRequestDto(content);
    }
}
