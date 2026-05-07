package com.ELShovi.initiallizer;

import com.ELShovi.model.Module;
import com.ELShovi.model.Role;
import com.ELShovi.model.User;
import com.ELShovi.repository.IModuleRepository;
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
    private final IModuleRepository moduleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        try {
            initializeModules();
            initializeAdminRole();
            initializeAdminUser();
        } catch (Exception e) {
            log.error("Error durante la inicialización del admin", e);
        }
    }

    private void initializeModules() {
        // Definir módulos principales del sistema
        String[][] modules = {
                {"USUARIOS", "Gestión de usuarios del sistema", "/admin/users"},
                {"ROLES", "Gestión de roles y permisos", "/admin/roles"},
                {"MODULOS", "Gestión de módulos del sistema", "/admin/modules"},
                {"PEDIDOS", "Gestión de órdenes/pedidos", "/orders"},
                {"MESAS", "Gestión de mesas del restaurante", "/tables"},
                {"MENU", "Gestión del menú y artículos", "/menu"},
                {"RESERVACIONES", "Gestión de reservaciones", "/reservations"},
                {"PAGOS", "Gestión de pagos", "/payments"},
                {"ENTREGAS", "Gestión de entregas", "/deliveries"},
                {"REPORTES", "Reportes y dashboards", "/dashboard"}
        };

        for (String[] moduleData : modules) {
            String name = moduleData[0];
            Optional<Module> existing = moduleRepository.findAll().stream()
                    .filter(m -> name.equalsIgnoreCase(m.getName()))
                    .findFirst();

            if (existing.isEmpty()) {
                Module module = new Module();
                module.setName(name);
                module.setDescription(moduleData[1]);
                module.setPath(moduleData[2]);
                module.setActive(true);
                moduleRepository.save(module);
                log.info("✓ Módulo creado: {}", name);
            }
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
            
            // Asignar todos los módulos al rol ADMIN
            List<Module> allModules = moduleRepository.findAll();
            newAdminRole.setModules(allModules);
            
            roleRepository.save(newAdminRole);
            log.info("✓ Rol ADMIN creado exitosamente con {} módulos", allModules.size());
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
