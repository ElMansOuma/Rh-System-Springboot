package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.model.Contrat;
import com.RH.gestion_collaborateurs.repository.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContratService {

    @Autowired
    private ContratRepository contratRepository;

    private final Path documentStorageLocation;

    public ContratService() {
        this.documentStorageLocation = Paths.get("uploads/contrats").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.documentStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    public Optional<Contrat> getContratById(Long id) {
        return contratRepository.findById(id);
    }

    public List<Contrat> getContratsByCollaborateurId(Long collaborateurId) {
        return contratRepository.findByCollaborateurId(collaborateurId);
    }

    public Contrat saveContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    public void deleteContrat(Long id) {
        contratRepository.deleteById(id);
    }

    public Contrat uploadDocument(Long id, MultipartFile document) {
        Optional<Contrat> contratOpt = contratRepository.findById(id);
        if (!contratOpt.isPresent()) {
            throw new RuntimeException("Contrat not found with id: " + id);
        }

        Contrat contrat = contratOpt.get();

        // Delete previous document if exists
        if (contrat.getDocumentUrl() != null && !contrat.getDocumentUrl().isEmpty()) {
            Path previousPath = this.documentStorageLocation.resolve(contrat.getDocumentUrl());
            try {
                Files.deleteIfExists(previousPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not delete previous document", e);
            }
        }

        // Generate unique filename
        String originalFileName = StringUtils.cleanPath(document.getOriginalFilename());
        String fileName = UUID.randomUUID() + "_" + originalFileName;

        try {
            Path targetLocation = this.documentStorageLocation.resolve(fileName);
            Files.copy(document.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            contrat.setDocumentUrl(fileName);
            return contratRepository.save(contrat);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource downloadDocument(Long id) {
        Optional<Contrat> contratOpt = contratRepository.findById(id);
        if (!contratOpt.isPresent() || contratOpt.get().getDocumentUrl() == null) {
            throw new RuntimeException("Document not found for contrat with id: " + id);
        }

        try {
            Path filePath = this.documentStorageLocation.resolve(contratOpt.get().getDocumentUrl()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found", ex);
        }
    }
}