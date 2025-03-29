package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.exception.CollaborateurNotFoundException;
import com.RH.gestion_collaborateurs.model.Collaborateur;
import com.RH.gestion_collaborateurs.model.Document;
import com.RH.gestion_collaborateurs.repository.CollaborateurRepository;
import com.RH.gestion_collaborateurs.repository.DocumentRepository;
import com.RH.gestion_collaborateurs.util.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public DocumentServiceImpl(
            DocumentRepository documentRepository,
            CollaborateurRepository collaborateurRepository,
            FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.collaborateurRepository = collaborateurRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Document saveDocument(Long collaborateurId, MultipartFile file, String documentType) throws IOException {
        Collaborateur collaborateur = collaborateurRepository.findById(collaborateurId)
                .orElseThrow(() -> new CollaborateurNotFoundException("Collaborateur non trouvé avec l'ID: " + collaborateurId));

        // Créer le sous-dossier pour les documents du collaborateur
        String subDirectory = "collaborateur-" + collaborateurId;
        fileStorageService.createDirectoryIfNotExists(subDirectory);

        // Générer un préfixe pour le nom du fichier
        String prefix = documentType.replaceAll("\\s", "_").toLowerCase();

        // Stocker le fichier et obtenir son chemin relatif
        String filePath = fileStorageService.storeFile(file, subDirectory, prefix);

        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setType(documentType);
        document.setDateAjout(LocalDate.now());
        document.setFilePath(filePath);
        document.setFileSize(file.getSize());
        document.setCollaborateur(collaborateur);

        return documentRepository.save(document);
    }

    @Override
    public List<Document> getDocumentsByCollaborateurId(Long collaborateurId) {
        return documentRepository.findByCollaborateurId(collaborateurId);
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé avec l'ID: " + id));
    }

    @Override
    public void deleteDocument(Long id) throws IOException {
        Document document = getDocumentById(id);

        // Supprimer le fichier physique
        fileStorageService.deleteFile(document.getFilePath());

        // Supprimer l'entrée de la base de données
        documentRepository.deleteById(id);
    }

    @Override
    public byte[] getDocumentContent(Long id) throws IOException {
        Document document = getDocumentById(id);
        Path path = fileStorageService.getFilePath(document.getFilePath());
        return Files.readAllBytes(path);
    }
}