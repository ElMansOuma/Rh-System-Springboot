package com.RH.gestion_collaborateurs.repository;

import com.RH.gestion_collaborateurs.model.MotifAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotifAbsenceRepository extends JpaRepository<MotifAbsence, Long> {
    Optional<MotifAbsence> findByCode(String code);
    Optional<MotifAbsence> findByLibelle(String libelle);
}