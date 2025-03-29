package com.RH.gestion_collaborateurs.repository;

import com.RH.gestion_collaborateurs.model.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {

    Optional<Collaborateur> findByMatricule(String matricule);

    Optional<Collaborateur> findByEmail(String email);

    Optional<Collaborateur> findByCin(String cin);

    List<Collaborateur> findByStatus(Collaborateur.Status status);

    @Query("SELECT c FROM Collaborateur c WHERE c.prenom LIKE %:term% OR c.nom LIKE %:term% OR c.email LIKE %:term% OR c.matricule LIKE %:term% OR c.titrePosteOccupe LIKE %:term%")
    List<Collaborateur> search(@Param("term") String term);

    @Query("SELECT c FROM Collaborateur c WHERE (:status IS NULL OR c.status = :status) AND " +
            "(:searchTerm IS NULL OR c.prenom LIKE %:searchTerm% OR c.nom LIKE %:searchTerm% OR " +
            "c.email LIKE %:searchTerm% OR c.matricule LIKE %:searchTerm% OR c.titrePosteOccupe LIKE %:searchTerm%)")
    List<Collaborateur> searchWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("status") Collaborateur.Status status);

    boolean existsByMatricule(String matricule);

    boolean existsByEmail(String email);

    boolean existsByCin(String cin);
}