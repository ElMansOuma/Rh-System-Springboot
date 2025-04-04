package com.RH.gestion_collaborateurs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private boolean active;
    private String profilePicture;
    private List<Long> assignedCollaborateurIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Long> getAssignedCollaborateurIds() {
        return assignedCollaborateurIds;
    }

    public void setAssignedCollaborateurIds(List<Long> assignedCollaborateurIds) {
        this.assignedCollaborateurIds = assignedCollaborateurIds;
    }
}