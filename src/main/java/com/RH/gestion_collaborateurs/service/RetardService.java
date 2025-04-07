package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.dto.RetardDTO;
import com.RH.gestion_collaborateurs.exception.ResourceNotFoundException;
import com.RH.gestion_collaborateurs.model.Collaborateur;
import com.RH.gestion_collaborateurs.model.Retard;
import com.RH.gestion_collaborateurs.repository.CollaborateurRepository;
import com.RH.gestion_collaborateurs.repository.RetardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RetardService {

    @Autowired
    private RetardRepository retardRepository;

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    public List<RetardDTO> getAllRetards() {
        List<Retard> retards = retardRepository.findAll();
        return retards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RetardDTO getRetardById(Long id) {
        Retard retard = retardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retard not found with id: " + id));
        return convertToDTO(retard);
    }

    public List<RetardDTO> getRetardsByCollaborateurId(Long collaborateurId) {
        List<Retard> retards = retardRepository.findByCollaborateurId(collaborateurId);
        return retards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RetardDTO> getRetardsByDate(LocalDate date) {
        List<Retard> retards = retardRepository.findByDate(date);
        return retards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RetardDTO> getRetardsByStatut(String statut) {
        List<Retard> retards = retardRepository.findByStatut(statut);
        return retards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RetardDTO> getRetardsByPeriod(LocalDate dateDebut, LocalDate dateFin) {
        List<Retard> retards = retardRepository.findByDateBetween(dateDebut, dateFin);
        return retards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RetardDTO createRetard(RetardDTO retardDTO) {
        Retard retard = convertToEntity(retardDTO);
        Retard savedRetard = retardRepository.save(retard);
        return convertToDTO(savedRetard);
    }

    @Transactional
    public RetardDTO updateRetard(Long id, RetardDTO retardDTO) {
        Retard existingRetard = retardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retard not found with id: " + id));

        updateRetardFromDTO(existingRetard, retardDTO);
        Retard updatedRetard = retardRepository.save(existingRetard);
        return convertToDTO(updatedRetard);
    }

    @Transactional
    public RetardDTO updateRetardStatus(Long id, String statut, String remarques) {
        Retard existingRetard = retardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retard not found with id: " + id));

        existingRetard.setStatut(statut);
        if (remarques != null && !remarques.isEmpty()) {
            existingRetard.setRemarques(remarques);
        }

        Retard updatedRetard = retardRepository.save(existingRetard);
        return convertToDTO(updatedRetard);
    }

    @Transactional
    public void deleteRetard(Long id) {
        if (!retardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Retard not found with id: " + id);
        }
        retardRepository.deleteById(id);
    }

    public Map<String, Object> getRetardStatsByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Retard> retards = retardRepository.findByDateBetween(startDate, endDate);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRetards", retards.size());
        stats.put("totalMinutesRetard", retards.stream().mapToInt(Retard::getDureeRetard).sum());

        // Group by month
        Map<Integer, Long> retardsByMonth = retards.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDate().getMonthValue(),
                        Collectors.counting()
                ));

        stats.put("retardsByMonth", retardsByMonth);

        // Group by status
        Map<String, Long> retardsByStatut = retards.stream()
                .collect(Collectors.groupingBy(
                        Retard::getStatut,
                        Collectors.counting()
                ));

        stats.put("retardsByStatut", retardsByStatut);

        return stats;
    }

    public Map<String, Object> getRetardStatsByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<Retard> retards = retardRepository.findByDateBetween(startDate, endDate);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRetards", retards.size());
        stats.put("totalMinutesRetard", retards.stream().mapToInt(Retard::getDureeRetard).sum());

        // Group by day
        Map<Integer, Long> retardsByDay = retards.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDate().getDayOfMonth(),
                        Collectors.counting()
                ));

        stats.put("retardsByDay", retardsByDay);

        // Group by collaborateur
        Map<String, Long> retardsByCollaborateur = retards.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCollaborateur().getNom() + " " + r.getCollaborateur().getPrenom(),
                        Collectors.counting()
                ));

        stats.put("retardsByCollaborateur", retardsByCollaborateur);

        // Group by status
        Map<String, Long> retardsByStatut = retards.stream()
                .collect(Collectors.groupingBy(
                        Retard::getStatut,
                        Collectors.counting()
                ));

        stats.put("retardsByStatut", retardsByStatut);

        return stats;
    }

    private RetardDTO convertToDTO(Retard retard) {
        RetardDTO dto = new RetardDTO();
        dto.setId(retard.getId());
        dto.setCollaborateurId(retard.getCollaborateur().getId());
        dto.setCollaborateurNom(retard.getCollaborateur().getNom() + " " + retard.getCollaborateur().getPrenom());
        dto.setDate(retard.getDate().toString());
        dto.setHeurePrevu(retard.getHeurePrevu().toString());
        dto.setHeureArrivee(retard.getHeureArrivee().toString());
        dto.setDureeRetard(retard.getDureeRetard());
        dto.setStatut(retard.getStatut());
        dto.setJustification(retard.getJustification());
        dto.setRemarques(retard.getRemarques());
        return dto;
    }

    private Retard convertToEntity(RetardDTO dto) {
        Retard retard = new Retard();

        if (dto.getId() != null) {
            retard.setId(dto.getId());
        }

        Collaborateur collaborateur = collaborateurRepository.findById(dto.getCollaborateurId())
                .orElseThrow(() -> new ResourceNotFoundException("Collaborateur not found with id: " + dto.getCollaborateurId()));

        retard.setCollaborateur(collaborateur);
        retard.setDate(LocalDate.parse(dto.getDate()));
        retard.setHeurePrevu(LocalTime.parse(dto.getHeurePrevu()));
        retard.setHeureArrivee(LocalTime.parse(dto.getHeureArrivee()));
        retard.setDureeRetard(dto.getDureeRetard());
        retard.setStatut(dto.getStatut());
        retard.setJustification(dto.getJustification());
        retard.setRemarques(dto.getRemarques());

        return retard;
    }

    private void updateRetardFromDTO(Retard retard, RetardDTO dto) {
        if (dto.getCollaborateurId() != null && !dto.getCollaborateurId().equals(retard.getCollaborateur().getId())) {
            Collaborateur collaborateur = collaborateurRepository.findById(dto.getCollaborateurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Collaborateur not found with id: " + dto.getCollaborateurId()));
            retard.setCollaborateur(collaborateur);
        }

        if (dto.getDate() != null) {
            retard.setDate(LocalDate.parse(dto.getDate()));
        }

        if (dto.getHeurePrevu() != null) {
            retard.setHeurePrevu(LocalTime.parse(dto.getHeurePrevu()));
        }

        if (dto.getHeureArrivee() != null) {
            retard.setHeureArrivee(LocalTime.parse(dto.getHeureArrivee()));
        }

        if (dto.getDureeRetard() != null) {
            retard.setDureeRetard(dto.getDureeRetard());
        }

        if (dto.getStatut() != null) {
            retard.setStatut(dto.getStatut());
        }

        if (dto.getJustification() != null) {
            retard.setJustification(dto.getJustification());
        }

        if (dto.getRemarques() != null) {
            retard.setRemarques(dto.getRemarques());
        }
    }
}