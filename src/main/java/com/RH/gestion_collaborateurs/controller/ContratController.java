package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.model.Contrat;
import com.RH.gestion_collaborateurs.service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@CrossOrigin(origins = "http://localhost:3000")
public class ContratController {

    @Autowired
    private ContratService contratService;
    @GetMapping
    public List<Contrat> getAllContrats() {
        return contratService.getAllContrats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrat> getContratById(@PathVariable Long id) {
        return contratService.getContratById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/collaborateur/{collaborateurId}")
    public List<Contrat> getContratsByCollaborateurId(@PathVariable Long collaborateurId) {
        return contratService.getContratsByCollaborateurId(collaborateurId);
    }

    @PostMapping
    public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) {
        Contrat savedContrat = contratService.saveContrat(contrat);
        return ResponseEntity.ok(savedContrat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrat> updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        if (!contratService.getContratById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        contrat.setId(id);
        Contrat updatedContrat = contratService.saveContrat(contrat);
        return ResponseEntity.ok(updatedContrat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        if (!contratService.getContratById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        contratService.deleteContrat(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/document")
    public ResponseEntity<Contrat> uploadDocument(@PathVariable Long id, @RequestParam("document") MultipartFile document) {
        Contrat updatedContrat = contratService.uploadDocument(id, document);
        return ResponseEntity.ok(updatedContrat);
    }

    @GetMapping("/{id}/document")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        Resource resource = contratService.downloadDocument(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}