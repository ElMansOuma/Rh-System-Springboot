package com.RH.gestion_collaborateurs.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RH.gestion_collaborateurs.model.Collaborateur;
import com.RH.gestion_collaborateurs.model.TimeRecord;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
    List<TimeRecord> findByCollaborateur(Collaborateur collaborateur);
    List<TimeRecord> findByDate(LocalDate date);
    List<TimeRecord> findByStatut(String statut);
    List<TimeRecord> findByCollaborateurAndDateBetween(Collaborateur collaborateur, LocalDate startDate, LocalDate endDate);
}