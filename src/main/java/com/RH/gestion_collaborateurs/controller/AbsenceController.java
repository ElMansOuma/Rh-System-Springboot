package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.model.Absence;
import com.RH.gestion_collaborateurs.model.MotifAbsence;
import com.RH.gestion_collaborateurs.service.AbsenceService;
import com.RH.gestion_collaborateurs.service.MotifAbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    private static final Logger logger = Logger.getLogger(AbsenceController.class.getName());

    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private MotifAbsenceService motifAbsenceService;

    @GetMapping
    public ResponseEntity<List<Absence>> getAllAbsences() {
        List<Absence> absences = absenceService.getAllAbsences();
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable Long id) {
        Absence absence = absenceService.getAbsenceById(id);
        return ResponseEntity.ok(absence);
    }

    @GetMapping("/collaborateur/{collaborateurId}")
    public ResponseEntity<List<Absence>> getAbsencesByCollaborateur(@PathVariable Long collaborateurId) {
        List<Absence> absences = absenceService.getAbsencesByCollaborateurId(collaborateurId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/motifs")
    public ResponseEntity<List<MotifAbsence>> getAllMotifs() {
        List<MotifAbsence> motifs = motifAbsenceService.getAllMotifs();
        return ResponseEntity.ok(motifs);
    }

    @PostMapping
    public ResponseEntity<?> createAbsence(@RequestBody Absence absence) {
        try {
            logger.info("Received absence creation request: " + absence);
            Absence createdAbsence = absenceService.createAbsence(absence);
            return new ResponseEntity<>(createdAbsence, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.severe("Error creating absence: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating absence: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAbsence(@PathVariable Long id, @RequestBody Absence absence) {
        try {
            Absence updatedAbsence = absenceService.updateAbsence(id, absence);
            return ResponseEntity.ok(updatedAbsence);
        } catch (Exception e) {
            logger.severe("Error updating absence: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating absence: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/observations")
    public ResponseEntity<Absence> updateObservations(
            @PathVariable Long id,
            @RequestBody String observations) {
        Absence updatedAbsence = absenceService.updateObservations(id, observations);
        return ResponseEntity.ok(updatedAbsence);
    }

    @PostMapping("/{id}/justificatif")
    public ResponseEntity<Absence> uploadJustificatif(
            @PathVariable Long id,
            @RequestParam("justificatif") MultipartFile file) throws IOException {
        Absence absence = absenceService.storeJustificatif(id, file);
        return ResponseEntity.ok(absence);
    }

    @GetMapping("/{id}/justificatif")
    public ResponseEntity<Resource> downloadJustificatif(@PathVariable Long id) throws IOException {
        Resource file = absenceService.loadJustificatif(id);
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}