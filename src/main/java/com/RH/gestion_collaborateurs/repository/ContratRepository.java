package com.RH.gestion_collaborateurs.repository;

import com.RH.gestion_collaborateurs.model.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findByCollaborateurId(Long collaborateurId);
}