package com.RH.gestion_collaborateurs.repository;

import com.RH.gestion_collaborateurs.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCollaborateurId(Long collaborateurId);
}