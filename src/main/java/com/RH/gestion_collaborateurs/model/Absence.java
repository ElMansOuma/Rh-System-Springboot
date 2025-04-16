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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MotifAbsence motif;

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

    // Vos getters et setters (vous pouvez les conserver ou les supprimer si vous utilisez Lombok @Data)
}
