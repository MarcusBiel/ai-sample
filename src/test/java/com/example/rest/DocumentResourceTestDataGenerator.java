package com.example.rest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentResourceTestDataGenerator {

    static final String ANY_CONTENT = "Test document content";
    static final String ANY_QUERY = "Test search query";
    static final long ANY_ID = 123L;

    public void initTestData() {
    }

    public void cleanupTestData() {
    }
}
