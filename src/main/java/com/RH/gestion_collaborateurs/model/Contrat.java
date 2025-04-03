package com.RH.gestion_collaborateurs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "contrats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long collaborateurId;

    @Column(nullable = false)
    private String numeroContrat;

    private String poste;

    private LocalDate dateEmbauche;

    @Column(nullable = false)
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(nullable = false)
    private String typeContrat;

    @Column(nullable = false)
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

    @Column(length = 1000)
    private String avantages;

    // Retenues
    private Double ir;
    private Double cnss;
    private Double cimr;
    private Double mutuelle;
    private Double retraite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContratStatus status = ContratStatus.Actif;

    private String documentUrl;

    public enum ContratStatus {
        Actif, Expiré, Résilié
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

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
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

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(Double salaireBase) {
        this.salaireBase = salaireBase;
    }

    public Integer getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(Integer anciennete) {
        this.anciennete = anciennete;
    }

    public String getModeEnPaiement() {
        return modeEnPaiement;
    }

    public void setModeEnPaiement(String modeEnPaiement) {
        this.modeEnPaiement = modeEnPaiement;
    }

    public Double getPrimeTransport() {
        return primeTransport;
    }

    public void setPrimeTransport(Double primeTransport) {
        this.primeTransport = primeTransport;
    }

    public Double getPrimePanier() {
        return primePanier;
    }

    public void setPrimePanier(Double primePanier) {
        this.primePanier = primePanier;
    }

    public Double getPrimeRepresentation() {
        return primeRepresentation;
    }

    public void setPrimeRepresentation(Double primeRepresentation) {
        this.primeRepresentation = primeRepresentation;
    }

    public Double getPrimeResponsabilite() {
        return primeResponsabilite;
    }

    public void setPrimeResponsabilite(Double primeResponsabilite) {
        this.primeResponsabilite = primeResponsabilite;
    }

    public Double getAutresPrimes() {
        return autresPrimes;
    }

    public void setAutresPrimes(Double autresPrimes) {
        this.autresPrimes = autresPrimes;
    }

    public Double getIndemnitesKilometriques() {
        return indemnitesKilometriques;
    }

    public void setIndemnitesKilometriques(Double indemnitesKilometriques) {
        this.indemnitesKilometriques = indemnitesKilometriques;
    }

    public Double getNoteDeFrais() {
        return noteDeFrais;
    }

    public void setNoteDeFrais(Double noteDeFrais) {
        this.noteDeFrais = noteDeFrais;
    }

    public String getAvantages() {
        return avantages;
    }

    public void setAvantages(String avantages) {
        this.avantages = avantages;
    }

    public Double getIr() {
        return ir;
    }

    public void setIr(Double ir) {
        this.ir = ir;
    }

    public Double getCnss() {
        return cnss;
    }

    public void setCnss(Double cnss) {
        this.cnss = cnss;
    }

    public Double getCimr() {
        return cimr;
    }

    public void setCimr(Double cimr) {
        this.cimr = cimr;
    }

    public Double getMutuelle() {
        return mutuelle;
    }

    public void setMutuelle(Double mutuelle) {
        this.mutuelle = mutuelle;
    }

    public Double getRetraite() {
        return retraite;
    }

    public void setRetraite(Double retraite) {
        this.retraite = retraite;
    }

    public ContratStatus getStatus() {
        return status;
    }

    public void setStatus(ContratStatus status) {
        this.status = status;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}