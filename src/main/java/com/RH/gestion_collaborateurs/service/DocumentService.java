package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    Document saveDocument(Long collaborateurId, MultipartFile file, String documentType) throws IOException;
    List<Document> getDocumentsByCollaborateurId(Long collaborateurId);
    Document getDocumentById(Long id);
    void deleteDocument(Long id) throws IOException;
    byte[] getDocumentContent(Long id) throws IOException;
}