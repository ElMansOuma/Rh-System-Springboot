package com.RH.gestion_collaborateurs.exception;

// Exception de collaborateur non trouvé
public class CollaborateurNotFoundException extends RuntimeException {

    public CollaborateurNotFoundException(String message) {
        super(message);
    }

    public CollaborateurNotFoundException(Long id) {
        super("Collaborateur non trouvé avec l'ID : " + id);
    }
}