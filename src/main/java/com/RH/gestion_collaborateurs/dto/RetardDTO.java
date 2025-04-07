package com.RH.gestion_collaborateurs.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetardDTO {

    private Long id;

    @NotNull(message = "L'ID du collaborateur est obligatoire")
    private Long collaborateurId;

    private String collaborateurNom;

    @NotBlank(message = "La date est obligatoire")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Le format de date doit être YYYY-MM-DD")
    private String date;

    @NotBlank(message = "L'heure prévue est obligatoire")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Le format de l'heure doit être HH:MM")
    private String heurePrevu;

    @NotBlank(message = "L'heure d'arrivée est obligatoire")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Le format de l'heure doit être HH:MM")
    private String heureArrivee;

    @NotNull(message = "La durée du retard est obligatoire")
    @Min(value = 1, message = "La durée du retard doit être positive")
    private Integer dureeRetard;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut;

    @NotBlank(message = "La justification est obligatoire")
    private String justification;

    private String remarques;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "L'ID du collaborateur est obligatoire") Long getCollaborateurId() {
        return collaborateurId;
    }

    public void setCollaborateurId(@NotNull(message = "L'ID du collaborateur est obligatoire") Long collaborateurId) {
        this.collaborateurId = collaborateurId;
    }

    public String getCollaborateurNom() {
        return collaborateurNom;
    }

    public void setCollaborateurNom(String collaborateurNom) {
        this.collaborateurNom = collaborateurNom;
    }

    public @NotBlank(message = "La date est obligatoire") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Le format de date doit être YYYY-MM-DD") String getDate() {
        return date;
    }

    public void setDate(@NotBlank(message = "La date est obligatoire") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Le format de date doit être YYYY-MM-DD") String date) {
        this.date = date;
    }

    public @NotBlank(message = "L'heure prévue est obligatoire") @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Le format de l'heure doit être HH:MM") String getHeurePrevu() {
        return heurePrevu;
    }

    public void setHeurePrevu(@NotBlank(message = "L'heure prévue est obligatoire") @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Le format de l'heure doit être HH:MM") String heurePrevu) {
        this.heurePrevu = heurePrevu;
    }

    public @NotBlank(message = "L'heure d'arrivée est obligatoire") @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Le format de l'heure doit être HH:MM") String getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(@NotBlank(message = "L'heure d'arrivée est obligatoire") @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Le format de l'heure doit être HH:MM") String heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public @NotNull(message = "La durée du retard est obligatoire") @Min(value = 1, message = "La durée du retard doit être positive") Integer getDureeRetard() {
        return dureeRetard;
    }

    public void setDureeRetard(@NotNull(message = "La durée du retard est obligatoire") @Min(value = 1, message = "La durée du retard doit être positive") Integer dureeRetard) {
        this.dureeRetard = dureeRetard;
    }

    public @NotBlank(message = "Le statut est obligatoire") String getStatut() {
        return statut;
    }

    public void setStatut(@NotBlank(message = "Le statut est obligatoire") String statut) {
        this.statut = statut;
    }

    public @NotBlank(message = "La justification est obligatoire") String getJustification() {
        return justification;
    }

    public void setJustification(@NotBlank(message = "La justification est obligatoire") String justification) {
        this.justification = justification;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }
}