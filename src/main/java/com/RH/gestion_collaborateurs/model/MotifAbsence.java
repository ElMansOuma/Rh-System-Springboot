package com.RH.gestion_collaborateurs.model;

public enum MotifAbsence {
    MALADIE("Maladie"),
    CONGE_PAYE("Congé payé"),
    CONGE_SANS_SOLDE("Congé sans solde"),
    FORMATION("Formation"),
    EVENEMENT_FAMILIAL("Événement familial"),
    AUTRE("Autre");

    private final String libelle;

    MotifAbsence(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
