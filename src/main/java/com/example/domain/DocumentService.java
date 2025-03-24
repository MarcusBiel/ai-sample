package com.example.domain;

import com.example.orm.DocumentEntity;
import com.example.orm.DocumentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DocumentService {

    private final DocumentRepository repository;
    private final OpenAIClient openAIClient;

    public DocumentService(DocumentRepository repository, OpenAIClient openAIClient) {
        this.repository = repository;
        this.openAIClient = openAIClient;
    }

    @Transactional
    public Document createDocument(String content) {
        float[] embedding = openAIClient.getEmbedding(content);
        return repository.save(Document.createNew(content, embedding));
    }

    public List<Document> searchDocuments(String query, int maxSearchResults) {
        float[] embedding = openAIClient.getEmbedding(query);
        return repository.findNearestNeighbors(embedding, maxSearchResults);
    }

    public List<Document> getAllDocuments() {
        return repository.getAllDocuments();
    }

    public Optional<Document> getDocumentById(Long id) {
        return repository.findByIdOptional(id)
                .map(this::toDomain);
    }

    @Transactional
    public boolean deleteDocumentById(Long id) {
        return repository.deleteById(id);
    }

    private Document toDomain(DocumentEntity entity) {
        return new Document(entity.getId(), entity.getContent(), entity.getEmbedding());
    }
}
