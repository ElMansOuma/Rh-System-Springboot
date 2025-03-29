package com.RH.gestion_collaborateurs.dto;


import com.RH.gestion_collaborateurs.model.Collaborateur.Sexe;
import com.RH.gestion_collaborateurs.model.Collaborateur.Status;
import com.RH.gestion_collaborateurs.model.Collaborateur.SituationFamiliale;
import com.RH.gestion_collaborateurs.model.Collaborateur.NiveauQualification;
import com.RH.gestion_collaborateurs.model.Collaborateur.SituationEntreprise;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CollaborateurResponseDTO {
    private Long id;
    private String matricule;
    private String prenom;
    private String nom;
    private Sexe sexe;
    private String cin;
    private LocalDate dateNaissance;
    private Status status;
    private String email;
    private String telephone;
    private String electionDomicile;
    private SituationFamiliale situationFamiliale;
    private Integer nombrePersonnesACharge;
    private String cnss;
    private Integer nombreAnneeExperience;
    private NiveauQualification niveauQualification;
    private String titrePosteOccupe;
    private String rib;
    private SituationEntreprise situationEntreprise;
    private LocalDate dateEmbauche;
    private String tachesAccomplies;
    private String photoUrl;
}