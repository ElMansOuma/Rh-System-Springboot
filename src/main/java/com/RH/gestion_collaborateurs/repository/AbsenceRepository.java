package com.RH.gestion_collaborateurs.repository;

import com.RH.gestion_collaborateurs.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByCollaborateurId(Long collaborateurId);
}