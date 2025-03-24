package com.example.domain;

import java.util.Arrays;
import java.util.Objects;

public final class Document {

    private final Long id;
    private final String content;
    private final float[] embedding;

    public Document(Long id, String content, float[] embedding) {
        this.id = id;
        this.content = content;
        this.embedding = copyOf(embedding);
    }

    public static Document createNew(String content, float[] embedding) {
        return new Document(null, content, embedding);
    }

    public static Document of(Long id, String content, float[] embedding) {
        return new Document(id, content, embedding);
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public float[] getEmbedding() {
        return copyOf(embedding);
    }

    private static float[] copyOf(float[] embedding) {
        return (embedding != null)
                ? Arrays.copyOf(embedding, embedding.length)
                : new float[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document doc)) return false;
        return Objects.equals(id, doc.id)
                && Objects.equals(content, doc.content)
                && Arrays.equals(embedding, doc.embedding);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, content);
        result = 31 * result + Arrays.hashCode(embedding);
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
