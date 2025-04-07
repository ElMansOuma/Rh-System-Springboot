package com.RH.gestion_collaborateurs.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RH.gestion_collaborateurs.dto.TimeRecordDTO;
import com.RH.gestion_collaborateurs.model.Collaborateur;
import com.RH.gestion_collaborateurs.model.TimeRecord;
import com.RH.gestion_collaborateurs.repository.CollaborateurRepository;
import com.RH.gestion_collaborateurs.repository.TimeRecordRepository;

@Service
public class TimeRecordService {

    @Autowired
    private TimeRecordRepository timeRecordRepository;

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    public List<TimeRecordDTO> getAllTimeRecords() {
        return timeRecordRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TimeRecordDTO getTimeRecordById(Long id) {
        Optional<TimeRecord> timeRecord = timeRecordRepository.findById(id);
        return timeRecord.map(this::convertToDTO).orElse(null);
    }

    public List<TimeRecordDTO> getTimeRecordsByCollaborateur(Long collaborateurId) {
        Optional<Collaborateur> collaborateur = collaborateurRepository.findById(collaborateurId);
        if (collaborateur.isPresent()) {
            return timeRecordRepository.findByCollaborateur(collaborateur.get()).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<TimeRecordDTO> getTimeRecordsByDate(LocalDate date) {
        return timeRecordRepository.findByDate(date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TimeRecordDTO> getTimeRecordsByStatut(String statut) {
        return timeRecordRepository.findByStatut(statut).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TimeRecordDTO createTimeRecord(TimeRecordDTO timeRecordDTO) {
        TimeRecord timeRecord = convertFromDTO(timeRecordDTO);

        // Calculate total hours if not provided
        if (timeRecord.getTotalHeures() == null && timeRecord.getHeureEntree() != null && timeRecord.getHeureSortie() != null) {
            Duration duration = Duration.between(timeRecord.getHeureEntree(), timeRecord.getHeureSortie());
            double totalHours = duration.toMinutes() / 60.0;
            timeRecord.setTotalHeures(totalHours);
        }

        // Check for retards and update status if needed
        if (timeRecord.getHeureEntree() != null && timeRecord.getHeureEntree().isAfter(LocalTime.of(9, 0))) {
            timeRecord.setStatut("Retard");
        }

        TimeRecord savedTimeRecord = timeRecordRepository.save(timeRecord);
        return convertToDTO(savedTimeRecord);
    }

    public TimeRecordDTO updateTimeRecord(Long id, TimeRecordDTO timeRecordDTO) {
        Optional<TimeRecord> existingTimeRecord = timeRecordRepository.findById(id);

        if (existingTimeRecord.isPresent()) {
            TimeRecord timeRecord = existingTimeRecord.get();

            // Update fields
            if (timeRecordDTO.getCollaborateurId() != null) {
                Optional<Collaborateur> collaborateur = collaborateurRepository.findById(timeRecordDTO.getCollaborateurId());
                collaborateur.ifPresent(timeRecord::setCollaborateur);
            }

            if (timeRecordDTO.getDate() != null) {
                timeRecord.setDate(timeRecordDTO.getDate());
            }

            if (timeRecordDTO.getHeureEntree() != null) {
                timeRecord.setHeureEntree(timeRecordDTO.getHeureEntree());
            }

            if (timeRecordDTO.getHeureSortie() != null) {
                timeRecord.setHeureSortie(timeRecordDTO.getHeureSortie());
            }

            if (timeRecordDTO.getStatut() != null) {
                timeRecord.setStatut(timeRecordDTO.getStatut());
            }

            if (timeRecordDTO.getJustification() != null) {
                timeRecord.setJustification(timeRecordDTO.getJustification());
            }

            // Recalculate total hours
            if (timeRecord.getHeureEntree() != null && timeRecord.getHeureSortie() != null) {
                Duration duration = Duration.between(timeRecord.getHeureEntree(), timeRecord.getHeureSortie());
                double totalHours = duration.toMinutes() / 60.0;
                timeRecord.setTotalHeures(totalHours);
            }

            TimeRecord updatedTimeRecord = timeRecordRepository.save(timeRecord);
            return convertToDTO(updatedTimeRecord);
        }

        return null;
    }

    public boolean deleteTimeRecord(Long id) {
        Optional<TimeRecord> timeRecord = timeRecordRepository.findById(id);

        if (timeRecord.isPresent()) {
            timeRecordRepository.delete(timeRecord.get());
            return true;
        }

        return false;
    }

    private TimeRecordDTO convertToDTO(TimeRecord timeRecord) {
        TimeRecordDTO dto = new TimeRecordDTO();
        dto.setId(timeRecord.getId());
        dto.setCollaborateurId(timeRecord.getCollaborateur().getId());
        dto.setCollaborateurNom(timeRecord.getCollaborateur().getNom() + " " + timeRecord.getCollaborateur().getPrenom());
        dto.setDate(timeRecord.getDate());
        dto.setHeureEntree(timeRecord.getHeureEntree());
        dto.setHeureSortie(timeRecord.getHeureSortie());
        dto.setTotalHeures(timeRecord.getTotalHeures());
        dto.setStatut(timeRecord.getStatut());
        dto.setJustification(timeRecord.getJustification());
        return dto;
    }

    private TimeRecord convertFromDTO(TimeRecordDTO dto) {
        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setId(dto.getId());

        if (dto.getCollaborateurId() != null) {
            Optional<Collaborateur> collaborateur = collaborateurRepository.findById(dto.getCollaborateurId());
            collaborateur.ifPresent(timeRecord::setCollaborateur);
        }

        timeRecord.setDate(dto.getDate());
        timeRecord.setHeureEntree(dto.getHeureEntree());
        timeRecord.setHeureSortie(dto.getHeureSortie());
        timeRecord.setTotalHeures(dto.getTotalHeures());
        timeRecord.setStatut(dto.getStatut());
        timeRecord.setJustification(dto.getJustification());
        return timeRecord;
    }
}