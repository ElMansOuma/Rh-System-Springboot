package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.dto.CollaborateurDTO;
import com.RH.gestion_collaborateurs.model.Collaborateur;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CollaborateurService {

    List<CollaborateurDTO> getAllCollaborateurs();

    CollaborateurDTO getCollaborateurById(Long id);

    CollaborateurDTO createCollaborateur(CollaborateurDTO collaborateurDTO);

    CollaborateurDTO updateCollaborateur(Long id, CollaborateurDTO collaborateurDTO);

    void deleteCollaborateur(Long id);

    List<CollaborateurDTO> searchCollaborateurs(String searchTerm, String status);

    CollaborateurDTO uploadPhoto(Long id, MultipartFile photo) throws IOException;
}