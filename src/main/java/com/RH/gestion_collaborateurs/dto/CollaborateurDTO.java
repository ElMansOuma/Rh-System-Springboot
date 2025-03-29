package com.RH.gestion_collaborateurs.dto;

import com.RH.gestion_collaborateurs.model.Collaborateur;
import java.time.LocalDate;

public class CollaborateurDTO {
    private Long id;
    private String matricule;
    private String prenom;
    private String nom;
    private String sexe;
    private String cin;
    private LocalDate dateNaissance;
    private String status;
    private String email;
    private String telephone;
    private String electionDomicile;
    private String situationFamiliale;
    private Integer nombrePersonnesACharge;
    private String cnss;
    private Integer nombreAnneeExperience;
    private String niveauQualification;
    private String titrePosteOccupe;
    private String rib;
    private String situationEntreprise;
    private LocalDate dateEmbauche;
    private String tachesAccomplies;
    private byte[] photo;

    // Constructeur vide
    public CollaborateurDTO() {
    }

    // Conversion Entity vers DTO
    public static CollaborateurDTO fromEntity(Collaborateur collaborateur) {
        CollaborateurDTO dto = new CollaborateurDTO();
        dto.setId(collaborateur.getId());
        dto.setMatricule(collaborateur.getMatricule());
        dto.setPrenom(collaborateur.getPrenom());
        dto.setNom(collaborateur.getNom());
        dto.setSexe(collaborateur.getSexe() != null ? collaborateur.getSexe().name() : null);
        dto.setCin(collaborateur.getCin());
        dto.setDateNaissance(collaborateur.getDateNaissance());
        dto.setStatus(collaborateur.getStatus() != null ? collaborateur.getStatus().name() : null);
        dto.setEmail(collaborateur.getEmail());
        dto.setTelephone(collaborateur.getTelephone());
        dto.setElectionDomicile(collaborateur.getElectionDomicile());
        dto.setSituationFamiliale(collaborateur.getSituationFamiliale() != null ? collaborateur.getSituationFamiliale().name() : null);
        dto.setNombrePersonnesACharge(collaborateur.getNombrePersonnesACharge());
        dto.setCnss(collaborateur.getCnss());
        dto.setNombreAnneeExperience(collaborateur.getNombreAnneeExperience());
        dto.setNiveauQualification(collaborateur.getNiveauQualification() != null ? collaborateur.getNiveauQualification().name() : null);
        dto.setTitrePosteOccupe(collaborateur.getTitrePosteOccupe());
        dto.setRib(collaborateur.getRib());
        dto.setSituationEntreprise(collaborateur.getSituationEntreprise() != null ? collaborateur.getSituationEntreprise().name() : null);
        dto.setDateEmbauche(collaborateur.getDateEmbauche());
        dto.setTachesAccomplies(collaborateur.getTachesAccomplies());
        dto.setPhoto(collaborateur.getPhoto());
        return dto;
    }

    // Conversion DTO vers Entity
    public Collaborateur toEntity() {
        Collaborateur collaborateur = new Collaborateur();
        collaborateur.setId(this.id);
        collaborateur.setMatricule(this.matricule);
        collaborateur.setPrenom(this.prenom);
        collaborateur.setNom(this.nom);

        if (this.sexe != null) {
            try {
                collaborateur.setSexe(Collaborateur.Sexe.valueOf(this.sexe));
            } catch (IllegalArgumentException e) {
                // Gestion d'erreur pour enum invalide
            }
        }

        collaborateur.setCin(this.cin);
        collaborateur.setDateNaissance(this.dateNaissance);

        if (this.status != null) {
            try {
                collaborateur.setStatus(Collaborateur.Status.valueOf(this.status));
            } catch (IllegalArgumentException e) {
                // Gestion d'erreur pour enum invalide
            }
        }

        collaborateur.setEmail(this.email);
        collaborateur.setTelephone(this.telephone);
        collaborateur.setElectionDomicile(this.electionDomicile);

        if (this.situationFamiliale != null) {
            try {
                collaborateur.setSituationFamiliale(Collaborateur.SituationFamiliale.valueOf(this.situationFamiliale));
            } catch (IllegalArgumentException e) {
                // Gestion d'erreur pour enum invalide
            }
        }

        collaborateur.setNombrePersonnesACharge(this.nombrePersonnesACharge);
        collaborateur.setCnss(this.cnss);
        collaborateur.setNombreAnneeExperience(this.nombreAnneeExperience);

        if (this.niveauQualification != null) {
            try {
                collaborateur.setNiveauQualification(Collaborateur.NiveauQualification.valueOf(this.niveauQualification));
            } catch (IllegalArgumentException e) {
                // Gestion d'erreur pour enum invalide
            }
        }

        collaborateur.setTitrePosteOccupe(this.titrePosteOccupe);
        collaborateur.setRib(this.rib);

        if (this.situationEntreprise != null) {
            try {
                collaborateur.setSituationEntreprise(Collaborateur.SituationEntreprise.valueOf(this.situationEntreprise));
            } catch (IllegalArgumentException e) {
                // Gestion d'erreur pour enum invalide
            }
        }

        collaborateur.setDateEmbauche(this.dateEmbauche);
        collaborateur.setTachesAccomplies(this.tachesAccomplies);
        collaborateur.setPhoto(this.photo);

        return collaborateur;
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

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(String situationFamiliale) {
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

    public String getNiveauQualification() {
        return niveauQualification;
    }

    public void setNiveauQualification(String niveauQualification) {
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

    public String getSituationEntreprise() {
        return situationEntreprise;
    }

    public void setSituationEntreprise(String situationEntreprise) {
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
}