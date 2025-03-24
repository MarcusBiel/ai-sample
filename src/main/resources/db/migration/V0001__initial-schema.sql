-- Enable pgvector extension (required for vector type)
CREATE EXTENSION IF NOT EXISTS vector;

-- Create document table with embedding vectors
CREATE TABLE document
(
    id        BIGSERIAL PRIMARY KEY,
    content   VARCHAR(2000) NOT NULL,
    embedding vector(1536)  NOT NULL
);
