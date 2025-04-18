package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.model.MotifAbsence;
import com.RH.gestion_collaborateurs.service.MotifAbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motifs-absence")
public class MotifAbsenceController {

    @Autowired
    private MotifAbsenceService motifAbsenceService;

    @GetMapping
    public ResponseEntity<List<MotifAbsence>> getAllMotifs() {
        List<MotifAbsence> motifs = motifAbsenceService.getAllMotifs();
        return ResponseEntity.ok(motifs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotifAbsence> getMotifById(@PathVariable Long id) {
        MotifAbsence motif = motifAbsenceService.getMotifById(id);
        return ResponseEntity.ok(motif);
    }

    @PostMapping
    public ResponseEntity<MotifAbsence> createMotif(@RequestBody MotifAbsence motif) {
        MotifAbsence createdMotif = motifAbsenceService.createMotif(motif);
        return new ResponseEntity<>(createdMotif, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotifAbsence> updateMotif(@PathVariable Long id, @RequestBody MotifAbsence motif) {
        MotifAbsence updatedMotif = motifAbsenceService.updateMotif(id, motif);
        return ResponseEntity.ok(updatedMotif);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotif(@PathVariable Long id) {
        motifAbsenceService.deleteMotif(id);
        return ResponseEntity.noContent().build();
    }
}