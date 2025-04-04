package com.RH.gestion_collaborateurs.service;

import com.RH.gestion_collaborateurs.dto.AdminCreateDTO;
import com.RH.gestion_collaborateurs.dto.AdminDTO;
import com.RH.gestion_collaborateurs.dto.AdminLoginDTO;
import com.RH.gestion_collaborateurs.dto.AdminResponseDTO;
import com.RH.gestion_collaborateurs.exception.ResourceNotFoundException;
import com.RH.gestion_collaborateurs.model.Admin;
import com.RH.gestion_collaborateurs.repository.AdminRepository;
import com.RH.gestion_collaborateurs.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager,
                        JwtTokenProvider tokenProvider) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public AdminResponseDTO createAdmin(AdminCreateDTO adminCreateDTO) {
        if (adminRepository.existsByEmail(adminCreateDTO.getEmail())) {
            throw new RuntimeException("Email est déjà utilisé!");
        }

        if (!adminCreateDTO.getPassword().equals(adminCreateDTO.getConfirmPassword())) {
            throw new RuntimeException("Les mots de passe ne correspondent pas!");
        }

        Admin admin = new Admin();
        admin.setFullName(adminCreateDTO.getFullName());
        admin.setEmail(adminCreateDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(adminCreateDTO.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);

        return mapToAdminResponseDTO(savedAdmin, null);
    }

    public AdminResponseDTO authenticateAdmin(AdminLoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        Admin admin = adminRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + loginDTO.getEmail()));

        return mapToAdminResponseDTO(admin, jwt);
    }

    public AdminDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        return mapToAdminDTO(admin);
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::mapToAdminDTO)
                .collect(Collectors.toList());
    }

    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        existingAdmin.setFullName(adminDTO.getFullName());
        existingAdmin.setActive(adminDTO.isActive());
        existingAdmin.setRole(adminDTO.getRole());

        if (adminDTO.getProfilePicture() != null) {
            existingAdmin.setProfilePicture(adminDTO.getProfilePicture());
        }

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return mapToAdminDTO(updatedAdmin);
    }

    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }

    public AdminDTO updatePassword(Long id, String currentPassword, String newPassword) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, admin.getPassword())) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }

        admin.setPassword(passwordEncoder.encode(newPassword));
        Admin updatedAdmin = adminRepository.save(admin);
        return mapToAdminDTO(updatedAdmin);
    }

    public AdminDTO mapToAdminDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setFullName(admin.getFullName());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setRole(admin.getRole());
        adminDTO.setActive(admin.isActive());
        adminDTO.setProfilePicture(admin.getProfilePicture());

        // Map assigned collaborateurs if needed
        if (admin.getAssignedCollaborateurs() != null) {
            adminDTO.setAssignedCollaborateurIds(
                    admin.getAssignedCollaborateurs().stream()
                            .map(collaborateur -> collaborateur.getId())
                            .collect(Collectors.toList())
            );
        }

        return adminDTO;
    }

    private AdminResponseDTO mapToAdminResponseDTO(Admin admin, String token) {
        AdminResponseDTO responseDTO = new AdminResponseDTO();
        responseDTO.setId(admin.getId());
        responseDTO.setFullName(admin.getFullName());
        responseDTO.setEmail(admin.getEmail());
        responseDTO.setRole(admin.getRole());
        responseDTO.setActive(admin.isActive());
        responseDTO.setProfilePicture(admin.getProfilePicture());
        responseDTO.setToken(token);
        return responseDTO;
    }
}