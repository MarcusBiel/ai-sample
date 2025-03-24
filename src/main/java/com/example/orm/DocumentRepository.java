package com.example.orm;

import com.example.domain.Document;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DocumentRepository implements PanacheRepositoryBase<DocumentEntity, Long> {

    public List<Document> getAllDocuments() {
        List<DocumentEntity> documentEntities = findAll().list();
        return DocumentEntityMapper.fromEntities(documentEntities);
    }

    public List<Document> findNearestNeighbors(float[] embedding, int limit) {
        List<DocumentEntity> documentEntities = getEntityManager().createNativeQuery("""
                        SELECT * FROM document
                        ORDER BY embedding <-> :vector
                        LIMIT :limit
                        """, DocumentEntity.class)
                .setParameter("vector", embedding)
                .setParameter("limit", limit)
                .getResultList();
        return DocumentEntityMapper.fromEntities(documentEntities);
    }

    @Transactional
    public Document save(Document document) {
        DocumentEntity entity = DocumentEntityMapper.toEntity(document);
        persist(entity);
        return DocumentEntityMapper.fromEntity(entity);
    }
}
