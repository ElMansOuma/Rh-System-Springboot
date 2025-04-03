package com.RH.gestion_collaborateurs.dto;

import com.RH.gestion_collaborateurs.model.Contrat.ContratStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratDTO {
    private Long id;
    private Long collaborateurId;
    private String numeroContrat;
    private String poste;
    private LocalDate dateEmbauche;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String typeContrat;
    private Double salaireBase;
    private Integer anciennete;
    private String modeEnPaiement;

    // Primes
    private Double primeTransport;
    private Double primePanier;
    private Double primeRepresentation;
    private Double primeResponsabilite;
    private Double autresPrimes;

    // Indemnités
    private Double indemnitesKilometriques;
    private Double noteDeFrais;
    private String avantages;

    // Retenues
    private Double ir;
    private Double cnss;
    private Double cimr;
    private Double mutuelle;
    private Double retraite;

    private ContratStatus status = ContratStatus.Actif;
    private String documentUrl;
}