package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.exception.ResourceNotFoundException;
import com.RH.gestion_collaborateurs.model.MotifAbsence;
import com.RH.gestion_collaborateurs.repository.MotifAbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotifAbsenceService {

    @Autowired
    private MotifAbsenceRepository motifAbsenceRepository;

    public List<MotifAbsence> getAllMotifs() {
        return motifAbsenceRepository.findAll();
    }

    public MotifAbsence getMotifById(Long id) {
        return motifAbsenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motif d'absence non trouvé avec l'id : " + id));
    }

    public MotifAbsence getMotifByCode(String code) {
        return motifAbsenceRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Motif d'absence non trouvé avec le code : " + code));
    }

    public MotifAbsence createMotif(MotifAbsence motif) {
        return motifAbsenceRepository.save(motif);
    }

    public MotifAbsence updateMotif(Long id, MotifAbsence motifDetails) {
        MotifAbsence motif = getMotifById(id);
        motif.setCode(motifDetails.getCode());
        motif.setLibelle(motifDetails.getLibelle());
        motif.setCouleur(motifDetails.getCouleur());
        return motifAbsenceRepository.save(motif);
    }

    public void deleteMotif(Long id) {
        MotifAbsence motif = getMotifById(id);
        motifAbsenceRepository.delete(motif);
    }
}