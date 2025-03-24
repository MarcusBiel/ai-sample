package com.example.rest;

import com.example.domain.Document;
import com.example.domain.DocumentService;
import com.example.rest.dto.CreateDocumentRequestDto;
import com.example.rest.dto.SearchRequestDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * REST resource for managing documents.
 */
@Path("/documents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentResource {

    private final DocumentService documentService;

    public DocumentResource(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Creates a new document from provided content.
     *
     * @param request DTO containing the content of the document.
     * @return The created document along with HTTP 201 status code.
     */
    @POST
    public Response createDocument(CreateDocumentRequestDto request) {
        if (request == null || request.content() == null || request.content().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Content must not be empty")
                    .build();
        }
        Document document = documentService.createDocument(request.content());
        return Response.status(Response.Status.CREATED).entity(document).build();
    }

    /**
     * Searches documents based on a textual query.
     *
     * @param request DTO containing search query and maximum results.
     * @return A list of documents matching the query, limited by maxSearchResults.
     */
    @POST
    @Path("/search")
    public List<Document> searchDocuments(SearchRequestDto request) {
        if (request == null || request.query() == null || request.query().isBlank()) {
            throw new BadRequestException("Query must not be empty");
        }
        int maxSearchResults = request.maxSearchResults() != null ? request.maxSearchResults() : 5;
        return documentService.searchDocuments(request.query(), maxSearchResults);
    }

    /**
     * Retrieves all stored documents.
     *
     * @return A list of all documents.
     */
    @GET
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    /**
     * Retrieves a specific document by its ID.
     *
     * @param id The unique identifier of the document.
     * @return The requested document, if found.
     * @throws NotFoundException if no document is found with the provided ID.
     */
    @GET
    @Path("/{id}")
    public Document getDocumentById(@PathParam("id") Long id) {
        return documentService.getDocumentById(id)
                .orElseThrow(() -> new NotFoundException("Document not found with id: " + id));
    }

    /**
     * Deletes a specific document by its ID.
     *
     * @param id The unique identifier of the document to delete.
     * @return HTTP 204 status code if deletion is successful.
     * @throws NotFoundException if no document is found with the provided ID.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteDocument(@PathParam("id") Long id) {
        boolean deleted = documentService.deleteDocumentById(id);
        if (!deleted) {
            throw new NotFoundException("Document not found with id: " + id);
        }
        return Response.noContent().build();
    }
}
