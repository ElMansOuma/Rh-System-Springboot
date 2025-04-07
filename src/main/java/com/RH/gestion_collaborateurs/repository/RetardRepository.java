package com.RH.gestion_collaborateurs.repository;


import com.RH.gestion_collaborateurs.model.Retard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RetardRepository extends JpaRepository<Retard, Long> {
    List<Retard> findByCollaborateurId(Long collaborateurId);
    List<Retard> findByDate(LocalDate date);
    List<Retard> findByStatut(String statut);
    List<Retard> findByDateBetween(LocalDate dateDebut, LocalDate dateFin);
}