package com.RH.gestion_collaborateurs.security;

import com.RH.gestion_collaborateurs.model.Admin;
import com.RH.gestion_collaborateurs.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin non trouvé avec email: " + email));

        return new User(
                admin.getEmail(),
                admin.getPassword(),
                admin.isActive(),
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority(admin.getRole()))
        );
    }
}