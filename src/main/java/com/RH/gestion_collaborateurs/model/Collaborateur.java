package com.RH.gestion_collaborateurs.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "collaborateurs")
public class Collaborateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricule;
    private String prenom;
    private String nom;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    private String cin;
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String email;
    private String telephone;
    private String electionDomicile;

    @Enumerated(EnumType.STRING)
    private SituationFamiliale situationFamiliale;

    private Integer nombrePersonnesACharge;
    private String cnss;
    private Integer nombreAnneeExperience;

    @Enumerated(EnumType.STRING)
    private NiveauQualification niveauQualification;

    private String titrePosteOccupe;
    private String rib;

    @Enumerated(EnumType.STRING)
    private SituationEntreprise situationEntreprise;

    private LocalDate dateEmbauche;

    @Column(length = 2000)
    private String tachesAccomplies;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photo;

    // Ajouter la relation avec Admin
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin assignedAdmin;

    // Enums internes
    public enum Sexe {
        Homme, Femme
    }

    public enum Status {
        Actif, Inactif
    }

    public enum SituationFamiliale {
        Célibataire, Marié, Divorcé
    }

    public enum NiveauQualification {
        Bac, Licence, Master, Doctorat
    }

    public enum SituationEntreprise {
        CDI, CDD, Freelance
    }

    // Constructeurs
    public Collaborateur() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getElectionDomicile() {
        return electionDomicile;
    }

    public void setElectionDomicile(String electionDomicile) {
        this.electionDomicile = electionDomicile;
    }

    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public Integer getNombrePersonnesACharge() {
        return nombrePersonnesACharge;
    }

    public void setNombrePersonnesACharge(Integer nombrePersonnesACharge) {
        this.nombrePersonnesACharge = nombrePersonnesACharge;
    }

    public String getCnss() {
        return cnss;
    }

    public void setCnss(String cnss) {
        this.cnss = cnss;
    }

    public Integer getNombreAnneeExperience() {
        return nombreAnneeExperience;
    }

    public void setNombreAnneeExperience(Integer nombreAnneeExperience) {
        this.nombreAnneeExperience = nombreAnneeExperience;
    }

    public NiveauQualification getNiveauQualification() {
        return niveauQualification;
    }

    public void setNiveauQualification(NiveauQualification niveauQualification) {
        this.niveauQualification = niveauQualification;
    }

    public String getTitrePosteOccupe() {
        return titrePosteOccupe;
    }

    public void setTitrePosteOccupe(String titrePosteOccupe) {
        this.titrePosteOccupe = titrePosteOccupe;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public SituationEntreprise getSituationEntreprise() {
        return situationEntreprise;
    }

    public void setSituationEntreprise(SituationEntreprise situationEntreprise) {
        this.situationEntreprise = situationEntreprise;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getTachesAccomplies() {
        return tachesAccomplies;
    }

    public void setTachesAccomplies(String tachesAccomplies) {
        this.tachesAccomplies = tachesAccomplies;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // Getter et Setter pour assignedAdmin
    public Admin getAssignedAdmin() {
        return assignedAdmin;
    }

    public void setAssignedAdmin(Admin assignedAdmin) {
        this.assignedAdmin = assignedAdmin;
    }
}