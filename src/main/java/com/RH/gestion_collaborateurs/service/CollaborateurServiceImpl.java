package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.dto.CollaborateurDTO;
import com.RH.gestion_collaborateurs.model.Collaborateur;
import com.RH.gestion_collaborateurs.exception.CollaborateurNotFoundException;
import com.RH.gestion_collaborateurs.repository.CollaborateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CollaborateurServiceImpl implements CollaborateurService {

    private final CollaborateurRepository collaborateurRepository;

    @Autowired
    public CollaborateurServiceImpl(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    @Override
    public List<CollaborateurDTO> getAllCollaborateurs() {
        return collaborateurRepository.findAll()
                .stream()
                .map(CollaborateurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CollaborateurDTO getCollaborateurById(Long id) {
        return collaborateurRepository.findById(id)
                .map(CollaborateurDTO::fromEntity)
                .orElseThrow(() -> new CollaborateurNotFoundException(id));
    }

    @Override
    public CollaborateurDTO createCollaborateur(CollaborateurDTO collaborateurDTO) {
        // Vérification d'unicité du matricule, email et CIN
        if (collaborateurDTO.getMatricule() != null &&
                collaborateurRepository.existsByMatricule(collaborateurDTO.getMatricule())) {
            throw new IllegalArgumentException("Un collaborateur avec ce matricule existe déjà");
        }

        if (collaborateurDTO.getEmail() != null &&
                collaborateurRepository.existsByEmail(collaborateurDTO.getEmail())) {
            throw new IllegalArgumentException("Un collaborateur avec cet email existe déjà");
        }

        if (collaborateurDTO.getCin() != null &&
                collaborateurRepository.existsByCin(collaborateurDTO.getCin())) {
            throw new IllegalArgumentException("Un collaborateur avec ce CIN existe déjà");
        }

        Collaborateur collaborateur = collaborateurDTO.toEntity();
        Collaborateur savedCollaborateur = collaborateurRepository.save(collaborateur);
        return CollaborateurDTO.fromEntity(savedCollaborateur);
    }

    @Override
    public CollaborateurDTO updateCollaborateur(Long id, CollaborateurDTO collaborateurDTO) {
        // Vérifier si le collaborateur existe
        Collaborateur existingCollaborateur = collaborateurRepository.findById(id)
                .orElseThrow(() -> new CollaborateurNotFoundException(id));

        // Vérifier l'unicité du matricule, email et CIN (sauf pour le même collaborateur)
        if (collaborateurDTO.getMatricule() != null &&
                !collaborateurDTO.getMatricule().equals(existingCollaborateur.getMatricule()) &&
                collaborateurRepository.existsByMatricule(collaborateurDTO.getMatricule())) {
            throw new IllegalArgumentException("Un autre collaborateur avec ce matricule existe déjà");
        }

        if (collaborateurDTO.getEmail() != null &&
                !collaborateurDTO.getEmail().equals(existingCollaborateur.getEmail()) &&
                collaborateurRepository.existsByEmail(collaborateurDTO.getEmail())) {
            throw new IllegalArgumentException("Un autre collaborateur avec cet email existe déjà");
        }

        if (collaborateurDTO.getCin() != null &&
                !collaborateurDTO.getCin().equals(existingCollaborateur.getCin()) &&
                collaborateurRepository.existsByCin(collaborateurDTO.getCin())) {
            throw new IllegalArgumentException("Un autre collaborateur avec ce CIN existe déjà");
        }

        // Conserver la photo existante si elle n'est pas fournie dans le DTO
        byte[] existingPhoto = existingCollaborateur.getPhoto();
        Collaborateur updatedCollaborateur = collaborateurDTO.toEntity();
        updatedCollaborateur.setId(id);

        if (collaborateurDTO.getPhoto() == null) {
            updatedCollaborateur.setPhoto(existingPhoto);
        }

        Collaborateur savedCollaborateur = collaborateurRepository.save(updatedCollaborateur);
        return CollaborateurDTO.fromEntity(savedCollaborateur);
    }

    @Override
    public void deleteCollaborateur(Long id) {
        if (!collaborateurRepository.existsById(id)) {
            throw new CollaborateurNotFoundException(id);
        }
        collaborateurRepository.deleteById(id);
    }

    @Override
    public List<CollaborateurDTO> searchCollaborateurs(String searchTerm, String status) {
        Collaborateur.Status statusEnum = null;
        if (status != null && !status.equalsIgnoreCase("Tous")) {
            try {
                statusEnum = Collaborateur.Status.valueOf(status);
            } catch (IllegalArgumentException e) {
                // Statut invalide, ignorer le filtre
            }
        }

        List<Collaborateur> results = collaborateurRepository.searchWithFilters(
                searchTerm, statusEnum);

        return results.stream()
                .map(CollaborateurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CollaborateurDTO uploadPhoto(Long id, MultipartFile photo) throws IOException {
        Collaborateur collaborateur = collaborateurRepository.findById(id)
                .orElseThrow(() -> new CollaborateurNotFoundException(id));

        collaborateur.setPhoto(photo.getBytes());
        Collaborateur updatedCollaborateur = collaborateurRepository.save(collaborateur);
        return CollaborateurDTO.fromEntity(updatedCollaborateur);
    }
}