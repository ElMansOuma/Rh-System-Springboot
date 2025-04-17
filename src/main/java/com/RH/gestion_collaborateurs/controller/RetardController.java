package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.dto.RetardDTO;
import com.RH.gestion_collaborateurs.service.RetardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/retards")

public class RetardController {

    @Autowired
    private RetardService retardService;

    @GetMapping
    public ResponseEntity<List<RetardDTO>> getAllRetards() {
        List<RetardDTO> retards = retardService.getAllRetards();
        return ResponseEntity.ok(retards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetardDTO> getRetardById(@PathVariable Long id) {
        RetardDTO retard = retardService.getRetardById(id);
        return ResponseEntity.ok(retard);
    }

    @GetMapping("/collaborateur/{collaborateurId}")
    public ResponseEntity<List<RetardDTO>> getRetardsByCollaborateur(@PathVariable Long collaborateurId) {
        List<RetardDTO> retards = retardService.getRetardsByCollaborateurId(collaborateurId);
        return ResponseEntity.ok(retards);
    }

    @GetMapping("/date")
    public ResponseEntity<List<RetardDTO>> getRetardsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<RetardDTO> retards = retardService.getRetardsByDate(date);
        return ResponseEntity.ok(retards);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<RetardDTO>> getRetardsByStatut(@PathVariable String statut) {
        List<RetardDTO> retards = retardService.getRetardsByStatut(statut);
        return ResponseEntity.ok(retards);
    }

    @GetMapping("/period")
    public ResponseEntity<List<RetardDTO>> getRetardsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        List<RetardDTO> retards = retardService.getRetardsByPeriod(dateDebut, dateFin);
        return ResponseEntity.ok(retards);
    }

    @PostMapping
    public ResponseEntity<RetardDTO> createRetard(@Valid @RequestBody RetardDTO retardDTO) {
        RetardDTO createdRetard = retardService.createRetard(retardDTO);
        return new ResponseEntity<>(createdRetard, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetardDTO> updateRetard(@PathVariable Long id, @Valid @RequestBody RetardDTO retardDTO) {
        RetardDTO updatedRetard = retardService.updateRetard(id, retardDTO);
        return ResponseEntity.ok(updatedRetard);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RetardDTO> updateRetardStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {

        String statut = statusUpdate.get("statut");
        String remarques = statusUpdate.get("remarques");

        RetardDTO updatedRetard = retardService.updateRetardStatus(id, statut, remarques);
        return ResponseEntity.ok(updatedRetard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRetard(@PathVariable Long id) {
        retardService.deleteRetard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/{year}")
    public ResponseEntity<Map<String, Object>> getYearlyStats(@PathVariable int year) {
        Map<String, Object> stats = retardService.getRetardStatsByYear(year);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/{year}/{month}")
    public ResponseEntity<Map<String, Object>> getMonthlyStats(
            @PathVariable int year,
            @PathVariable int month) {
        Map<String, Object> stats = retardService.getRetardStatsByMonth(year, month);
        return ResponseEntity.ok(stats);
    }
}