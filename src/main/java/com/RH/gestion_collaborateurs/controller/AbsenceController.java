package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.model.Absence;
import com.RH.gestion_collaborateurs.model.MotifAbsence;
import com.RH.gestion_collaborateurs.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "*")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

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
    public ResponseEntity<List<String>> getAllMotifs() {
        List<String> motifs = Arrays.stream(MotifAbsence.values())
                .map(MotifAbsence::getLibelle)
                .collect(Collectors.toList());
        return ResponseEntity.ok(motifs);
    }

    @PostMapping
    public ResponseEntity<Absence> createAbsence(@RequestBody Absence absence) {
        Absence createdAbsence = absenceService.createAbsence(absence);
        return new ResponseEntity<>(createdAbsence, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Absence> updateAbsence(@PathVariable Long id, @RequestBody Absence absence) {
        Absence updatedAbsence = absenceService.updateAbsence(id, absence);
        return ResponseEntity.ok(updatedAbsence);
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
