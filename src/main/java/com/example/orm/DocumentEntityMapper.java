package com.example.orm;

import com.example.domain.Document;

import java.util.List;

public final class DocumentEntityMapper {

    public static DocumentEntity toEntity(Document document) {
        return DocumentEntity.of(
                document.getContent(),
                document.getEmbedding()
        );
    }

    public static Document fromEntity(DocumentEntity entity) {
        return new Document(
                entity.getId(),
                entity.getContent(),
                entity.getEmbedding()
        );
    }

    public static List<Document> fromEntities(List<DocumentEntity> entities) {
        return entities.stream()
                .map(DocumentEntityMapper::fromEntity)
                .toList();
    }

    private DocumentEntityMapper() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }
}