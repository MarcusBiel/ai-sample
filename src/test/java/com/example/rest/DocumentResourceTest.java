package com.example.rest;

import com.example.domain.Document;
import com.example.domain.DocumentService;
import com.example.rest.dto.CreateDocumentRequestDto;
import com.example.rest.dto.SearchRequestDto;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.rest.DocumentResourceTestDataGenerator.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(DocumentResource.class)
@Tag("integration")
class DocumentResourceTest {

    @Inject
    DocumentResourceTestDataGenerator testDataGenerator;

    @InjectMock
    DocumentService documentService;

    @BeforeEach
    @Transactional
    void beforeEach() {
        testDataGenerator.initTestData();
    }

    @AfterEach
    @Transactional
    void afterEach() {
        testDataGenerator.cleanupTestData();
    }

    @Test
    void createDocument() {
        when(documentService.createDocument(any()))
                .thenReturn(Document.of(ANY_ID, ANY_CONTENT, null));

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(CreateDocumentRequestDto.of(ANY_CONTENT))
                .when()
                .post()
                .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", is((int) ANY_ID))
                .body("content", is(ANY_CONTENT));

        verify(documentService).createDocument(ANY_CONTENT);
    }

    @Test
    void searchDocuments() {
        List<Document> documents = List.of(Document.of(ANY_ID, ANY_CONTENT, null));
        when(documentService.searchDocuments(any(), any(Integer.class)))
                .thenReturn(documents);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(SearchRequestDto.of(ANY_QUERY, 5))
                .when()
                .post("/search")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("[0].id", is((int) ANY_ID))
                .body("[0].content", is(ANY_CONTENT));

        verify(documentService).searchDocuments(ANY_QUERY, 5);
    }

    @Test
    void getDocumentById() {
        when(documentService.getDocumentById(ANY_ID))
                .thenReturn(Optional.of(Document.of(ANY_ID, ANY_CONTENT, null)));

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}", ANY_ID)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is((int) ANY_ID))
                .body("content", is(ANY_CONTENT));

        verify(documentService).getDocumentById(ANY_ID);
    }
}
