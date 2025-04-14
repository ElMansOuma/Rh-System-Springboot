package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.model.Document;
import com.RH.gestion_collaborateurs.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")

public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload/{collaborateurId}")
    public ResponseEntity<DocumentDTO> uploadDocument(
            @PathVariable Long collaborateurId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType) throws IOException {

        Document document = documentService.saveDocument(collaborateurId, file, documentType);
        return ResponseEntity.ok(convertToDTO(document));
    }

    @GetMapping("/collaborateur/{collaborateurId}")
    public ResponseEntity<List<DocumentDTO>> getDocumentsByCollaborateur(@PathVariable Long collaborateurId) {
        List<Document> documents = documentService.getDocumentsByCollaborateurId(collaborateurId);
        List<DocumentDTO> documentDTOs = documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(documentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(convertToDTO(document));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) throws IOException {
        Document document = documentService.getDocumentById(id);
        byte[] data = documentService.getDocumentContent(id);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                .contentLength(document.getFileSize())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<Resource> viewDocument(@PathVariable Long id) throws IOException {
        Document document = documentService.getDocumentById(id);
        byte[] data = documentService.getDocumentContent(id);
        ByteArrayResource resource = new ByteArrayResource(data);

        // Déterminer le MediaType approprié en fonction de l'extension du fichier
        MediaType mediaType = determineMediaType(document.getName());

        return ResponseEntity.ok()
                .contentLength(document.getFileSize())
                .contentType(mediaType)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) throws IOException {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    // Méthode utilitaire pour déterminer le type MIME
    private MediaType determineMediaType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "doc":
                return MediaType.parseMediaType("application/msword");
            case "docx":
                return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    // DTO pour le transfert des données de document
    private static class DocumentDTO {
        private Long id;
        private String name;
        private String type;
        private String date;
        private String size;

        // Getters et setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }

    private DocumentDTO convertToDTO(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setType(document.getType());
        dto.setDate(document.getDateAjout().toString());

        // Convertir la taille en format lisible (KB, MB)
        long size = document.getFileSize();
        String readableSize;
        if (size < 1024) {
            readableSize = size + " B";
        } else if (size < 1024 * 1024) {
            readableSize = (size / 1024) + " KB";
        } else {
            readableSize = String.format("%.2f MB", size / (1024.0 * 1024.0));
        }
        dto.setSize(readableSize);

        return dto;
    }
}