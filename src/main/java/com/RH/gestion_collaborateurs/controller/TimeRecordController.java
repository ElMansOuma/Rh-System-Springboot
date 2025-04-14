package com.RH.gestion_collaborateurs.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RH.gestion_collaborateurs.dto.TimeRecordDTO;
import com.RH.gestion_collaborateurs.service.TimeRecordService;

@RestController
@RequestMapping("/api/pointages")
@CrossOrigin(origins = "*")
public class TimeRecordController {

    @Autowired
    private TimeRecordService timeRecordService;

    @GetMapping
    public ResponseEntity<List<TimeRecordDTO>> getAllTimeRecords() {
        List<TimeRecordDTO> timeRecords = timeRecordService.getAllTimeRecords();
        return new ResponseEntity<>(timeRecords, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeRecordDTO> getTimeRecordById(@PathVariable Long id) {
        TimeRecordDTO timeRecord = timeRecordService.getTimeRecordById(id);
        if (timeRecord != null) {
            return new ResponseEntity<>(timeRecord, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/collaborateur/{collaborateurId}")
    public ResponseEntity<List<TimeRecordDTO>> getTimeRecordsByCollaborateur(@PathVariable Long collaborateurId) {
        List<TimeRecordDTO> timeRecords = timeRecordService.getTimeRecordsByCollaborateur(collaborateurId);
        return new ResponseEntity<>(timeRecords, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<TimeRecordDTO>> getTimeRecordsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TimeRecordDTO> timeRecords = timeRecordService.getTimeRecordsByDate(date);
        return new ResponseEntity<>(timeRecords, HttpStatus.OK);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<TimeRecordDTO>> getTimeRecordsByStatut(@PathVariable String statut) {
        List<TimeRecordDTO> timeRecords = timeRecordService.getTimeRecordsByStatut(statut);
        return new ResponseEntity<>(timeRecords, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TimeRecordDTO> createTimeRecord(@RequestBody TimeRecordDTO timeRecordDTO) {
        TimeRecordDTO newTimeRecord = timeRecordService.createTimeRecord(timeRecordDTO);
        return new ResponseEntity<>(newTimeRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeRecordDTO> updateTimeRecord(@PathVariable Long id, @RequestBody TimeRecordDTO timeRecordDTO) {
        TimeRecordDTO updatedTimeRecord = timeRecordService.updateTimeRecord(id, timeRecordDTO);
        if (updatedTimeRecord != null) {
            return new ResponseEntity<>(updatedTimeRecord, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeRecord(@PathVariable Long id) {
        boolean deleted = timeRecordService.deleteTimeRecord(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}