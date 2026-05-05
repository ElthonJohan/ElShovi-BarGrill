package com.ELShovi.initiallizer;

import com.ELShovi.model.Role;
import com.ELShovi.model.User;
import com.ELShovi.repository.IRoleRepository;
import com.ELShovi.repository.IUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialRoleAdmin {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        try {
            initializeAdminRole();
            initializeAdminUser();
        } catch (Exception e) {
            log.error("Error durante la inicialización del admin", e);
        }
    }

    private void initializeAdminRole() {
        Optional<Role> adminRole = roleRepository.findAll().stream()
                .filter(role -> "ADMIN".equalsIgnoreCase(role.getName()))
                .findFirst();

        if (adminRole.isEmpty()) {
            Role newAdminRole = new Role();
            newAdminRole.setName("ADMIN");
            newAdminRole.setDescription("Administrador del sistema con acceso completo");
            roleRepository.save(newAdminRole);
            log.info("✓ Rol ADMIN creado exitosamente");
        } else {
            log.info("✓ Rol ADMIN ya existe");
        }
    }

    private void initializeAdminUser() {
        String adminEmail = "admin@elshovi.com";
        Optional<User> existingAdmin = userRepository.findByEmail(adminEmail);

        if (existingAdmin.isEmpty()) {
            Role adminRole = roleRepository.findAll().stream()
                    .filter(role -> "ADMIN".equalsIgnoreCase(role.getName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

            User adminUser = new User();
            adminUser.setEmail(adminEmail);
            adminUser.setUserName("admin");
            adminUser.setFullName("Administrador del Sistema");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Contraseña por defecto (CAMBIAR EN PRODUCCIÓN)
            adminUser.setActive(true);
            adminUser.setRoles(List.of(adminRole));

            userRepository.save(adminUser);
            log.info("✓ Usuario ADMIN creado exitosamente");
            log.warn("⚠ Credenciales por defecto - Cambiar contraseña en producción:");
            log.warn("  Email: {}", adminEmail);
            log.warn("  Usuario: admin");
            log.warn("  Contraseña: admin123");
        } else {
            log.info("✓ Usuario ADMIN ya existe");
        }
    }
}
