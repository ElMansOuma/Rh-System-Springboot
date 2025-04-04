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
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        return ResponseEntity.ok(adminService.updateAdmin(id, adminDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<AdminDTO> changePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> passwordData) {

        String currentPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");

        if (currentPassword == null || newPassword == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(adminService.updatePassword(id, currentPassword, newPassword));
    }

    @GetMapping("/me")
    public ResponseEntity<AdminDTO> getCurrentAdmin() {
        // Obtenez l'utilisateur connecté à partir du contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + email));

        return ResponseEntity.ok(adminService.mapToAdminDTO(admin));
    }
}