package com.example.orm;

import jakarta.persistence.*;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "document")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000, nullable = false)
    private String content;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 1536)
    @Column(columnDefinition = "vector(1536)", nullable = false)
    private float[] embedding;

    public static DocumentEntity of(String content, float[] embedding) {
        return new DocumentEntity(content, embedding);
    }

    private DocumentEntity(String content, float[] embedding) {
        this.content = content;
        this.embedding = embedding;
    }

    public DocumentEntity() {
        // Required by Hibernate
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public float[] getEmbedding() {
        return embedding;
    }
}
