package com.RH.gestion_collaborateurs.payload;

import lombok.Data;

@Data
public class StatusUpdateRequest {
    private String status;
    private String observations;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}