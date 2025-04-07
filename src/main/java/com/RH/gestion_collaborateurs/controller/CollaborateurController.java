package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.dto.CollaborateurDTO;
import com.RH.gestion_collaborateurs.service.CollaborateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/collaborateurs")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Collaborateur", description = "API de gestion des collaborateurs")
public class CollaborateurController {

    private final CollaborateurService collaborateurService;



    @Autowired
    public CollaborateurController(CollaborateurService collaborateurService) {
        this.collaborateurService = collaborateurService;
    }
    @Operation(summary = "Liste tous les collaborateurs",
            description = "Renvoie la liste de tous les collaborateurs enregistrés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    @GetMapping
    public ResponseEntity<List<CollaborateurDTO>> getAllCollaborateurs() {
        List<CollaborateurDTO> collaborateurs = collaborateurService.getAllCollaborateurs();
        return ResponseEntity.ok(collaborateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollaborateurDTO> getCollaborateurById(@PathVariable Long id) {
        CollaborateurDTO collaborateur = collaborateurService.getCollaborateurById(id);
        return ResponseEntity.ok(collaborateur);
    }

    @PostMapping
    public ResponseEntity<CollaborateurDTO> createCollaborateur(@RequestBody CollaborateurDTO collaborateurDTO) {
        CollaborateurDTO createdCollaborateur = collaborateurService.createCollaborateur(collaborateurDTO);
        return new ResponseEntity<>(createdCollaborateur, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollaborateurDTO> updateCollaborateur(
            @PathVariable Long id,
            @RequestBody CollaborateurDTO collaborateurDTO) {
        CollaborateurDTO updatedCollaborateur = collaborateurService.updateCollaborateur(id, collaborateurDTO);
        return ResponseEntity.ok(updatedCollaborateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollaborateur(@PathVariable Long id) {
        collaborateurService.deleteCollaborateur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CollaborateurDTO>> searchCollaborateurs(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String status) {
        List<CollaborateurDTO> collaborateurs = collaborateurService.searchCollaborateurs(searchTerm, status);
        return ResponseEntity.ok(collaborateurs);
    }

    @PostMapping(value = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CollaborateurDTO> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("photo") MultipartFile photo) {
        try {
            CollaborateurDTO updatedCollaborateur = collaborateurService.uploadPhoto(id, photo);
            return ResponseEntity.ok(updatedCollaborateur);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        CollaborateurDTO collaborateur = collaborateurService.getCollaborateurById(id);
        byte[] photoData = collaborateur.getPhoto();
        if (photoData == null || photoData.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photoData);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
    }
}