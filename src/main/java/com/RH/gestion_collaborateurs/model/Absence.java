package com.RH.gestion_collaborateurs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "absences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "collaborateur_id", nullable = false)
    private Long collaborateurId;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false)
    private String motif;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "justificatif_path")
    private String justificatifPath;

    @Column(name = "justificatif_nom")
    private String justificatifNom;

    @Transient
    public String getJustificatifUrl() {
        if (justificatifPath == null) return null;
        return "/api/absences/" + id + "/justificatif";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollaborateurId() {
        return collaborateurId;
    }

    public void setCollaborateurId(Long collaborateurId) {
        this.collaborateurId = collaborateurId;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getJustificatifPath() {
        return justificatifPath;
    }

    public void setJustificatifPath(String justificatifPath) {
        this.justificatifPath = justificatifPath;
    }

    public String getJustificatifNom() {
        return justificatifNom;
    }

    public void setJustificatifNom(String justificatifNom) {
        this.justificatifNom = justificatifNom;
    }
}