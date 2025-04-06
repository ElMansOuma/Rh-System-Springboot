package com.RH.gestion_collaborateurs.controller;

import com.RH.gestion_collaborateurs.dto.AdminCreateDTO;
import com.RH.gestion_collaborateurs.dto.AdminDTO;
import com.RH.gestion_collaborateurs.dto.AdminLoginDTO;
import com.RH.gestion_collaborateurs.dto.AdminResponseDTO;
import com.RH.gestion_collaborateurs.exception.ResourceNotFoundException;
import com.RH.gestion_collaborateurs.model.Admin;
import com.RH.gestion_collaborateurs.repository.AdminRepository;
import com.RH.gestion_collaborateurs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminService adminService, AdminRepository adminRepository) {
        this.adminService = adminService;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AdminResponseDTO> registerAdmin(@RequestBody AdminCreateDTO adminCreateDTO) {
        return new ResponseEntity<>(adminService.createAdmin(adminCreateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AdminResponseDTO> loginAdmin(@RequestBody AdminLoginDTO loginDTO) {
        return ResponseEntity.ok(adminService.authenticateAdmin(loginDTO));
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody Map<String, String> updateData) {
        // Extract only the fields we need for profile update
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setFullName(updateData.get("fullName"));
        adminDTO.setEmail(updateData.get("email"));

        // We're keeping the other fields unchanged
        AdminDTO currentAdmin = adminService.getAdminById(id);
        adminDTO.setRole(currentAdmin.getRole());
        adminDTO.setActive(currentAdmin.isActive());
        adminDTO.setProfilePicture(currentAdmin.getProfilePicture());
        adminDTO.setAssignedCollaborateurIds(currentAdmin.getAssignedCollaborateurIds());

        return ResponseEntity.ok(adminService.updateAdmin(id, adminDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> passwordData) {

        String currentPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");

        if (currentPassword == null || newPassword == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Les champs mot de passe actuel et nouveau mot de passe sont requis"
            ));
        }

        try {
            AdminDTO updatedAdmin = adminService.updatePassword(id, currentPassword, newPassword);
            return ResponseEntity.ok(Map.of(
                    "message", "Mot de passe mis à jour avec succès",
                    "admin", updatedAdmin
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<AdminDTO> getCurrentAdmin() {
        // Get the connected user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + email));

        return ResponseEntity.ok(adminService.mapToAdminDTO(admin));
    }
}