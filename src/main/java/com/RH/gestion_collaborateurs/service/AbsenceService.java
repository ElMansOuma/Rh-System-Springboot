package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.exception.ResourceNotFoundException;
import com.RH.gestion_collaborateurs.model.Absence;
import com.RH.gestion_collaborateurs.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.UUID;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Value("${app.upload.dir:uploads/justificatifs}")

    private final Path fileStoragePath;

    public AbsenceService(@Value("${app.upload.dir:uploads/justificatifs}") String uploadDir) {
        this.fileStoragePath = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException ex) {
            throw new RuntimeException("Impossible de créer le répertoire de stockage des fichiers.", ex);
        }
    }

    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    public Absence getAbsenceById(Long id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence non trouvée avec l'id : " + id));
    }

    public List<Absence> getAbsencesByCollaborateurId(Long collaborateurId) {
        return absenceRepository.findByCollaborateurId(collaborateurId);
    }

    public Absence createAbsence(Absence absence) {
        return absenceRepository.save(absence);
    }

    public Absence updateAbsence(Long id, Absence absenceDetails) {
        Absence absence = getAbsenceById(id);

        absence.setCollaborateurId(absenceDetails.getCollaborateurId());
        absence.setDateDebut(absenceDetails.getDateDebut());
        absence.setDateFin(absenceDetails.getDateFin());
        absence.setMotif(absenceDetails.getMotif());
        absence.setObservations(absenceDetails.getObservations());

        return absenceRepository.save(absence);
    }

    public void deleteAbsence(Long id) {
        Absence absence = getAbsenceById(id);

        // Supprimer le justificatif si existant
        if (absence.getJustificatifPath() != null) {
            try {
                Path filePath = fileStoragePath.resolve(absence.getJustificatifPath());
                Files.deleteIfExists(filePath);
            } catch (IOException ex) {
                // Log l'erreur mais continue la suppression
                System.err.println("Impossible de supprimer le fichier justificatif: " + ex.getMessage());
            }
        }

        absenceRepository.delete(absence);
    }

    public Absence updateObservations(Long id, String observations) {
        Absence absence = getAbsenceById(id);

        if (observations != null) {
            absence.setObservations(observations);
        }

        return absenceRepository.save(absence);
    }

    public Absence storeJustificatif(Long id, MultipartFile file) throws IOException {
        Absence absence = getAbsenceById(id);

        // Supprimer l'ancien justificatif si existant
        if (absence.getJustificatifPath() != null) {
            Path oldFilePath = fileStoragePath.resolve(absence.getJustificatifPath());
            Files.deleteIfExists(oldFilePath);
        }

        // Générer un nom de fichier unique
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        // Stocker le fichier
        Path targetLocation = fileStoragePath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Mettre à jour l'entité absence
        absence.setJustificatifPath(uniqueFileName);
        absence.setJustificatifNom(fileName);

        return absenceRepository.save(absence);
    }

    public Resource loadJustificatif(Long id) throws IOException {
        Absence absence = getAbsenceById(id);

        if (absence.getJustificatifPath() == null) {
            throw new ResourceNotFoundException("Aucun justificatif trouvé pour cette absence");
        }

        try {
            Path filePath = fileStoragePath.resolve(absence.getJustificatifPath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("Fichier justificatif introuvable: " + absence.getJustificatifNom());
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("Fichier justificatif introuvable: " + absence.getJustificatifNom());
        }
    }
}
