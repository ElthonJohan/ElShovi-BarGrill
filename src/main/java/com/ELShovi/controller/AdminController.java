package com.ELShovi.controller;

import com.ELShovi.dto.*;
import com.ELShovi.model.Module;
import com.ELShovi.model.Role;
import com.ELShovi.model.User;
import com.ELShovi.service.IModuleService;
import com.ELShovi.service.IRoleService;
import com.ELShovi.service.IUserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class AdminController {

    private final IUserService userService;
    private final IRoleService roleService;
    private final IModuleService moduleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // ======================= GESTIÓN DE USUARIOS =======================

    /**
     * Obtener todos los usuarios
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception {
        List<User> users = userService.findAll();
        List<UserDTO> dtos = users.stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener usuario por ID
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) throws Exception {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertUserToDTO(user));
    }

    /**
     * Crear nuevo usuario con roles asignados
     */
    @PostMapping("/users")
    @Transactional
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO dto) throws Exception {
        // Validar que el email no exista
        User existingUser = userService.findByEmail(dto.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Crear usuario
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setActive(true);

        // Asignar roles
        List<Role> roles = dto.getRoleIds().stream()
                .map(roleId -> {
                    try {
                        return roleService.findById(roleId);
                    } catch (Exception e) {
                        throw new RuntimeException("Rol no encontrado: " + roleId, e);
                    }
                })
                .collect(Collectors.toList());

        user.setRoles(roles);

        // Guardar usuario
        User saved = userService.save(user);
        log.info("✓ Usuario creado: {}", saved.getEmail());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdUser())
                .toUri();

        return ResponseEntity.created(location).body(convertUserToDTO(saved));
    }

    /**
     * Editar usuario
     */
    @PutMapping("/users/{id}")
    @Transactional
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserDTO dto) throws Exception {

        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setFullName(dto.getFullName());
        user.setActive(dto.isActive());

        // Si viene contraseña, encriptarla
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updated = userService.update(user, id);
        log.info("✓ Usuario actualizado: {}", updated.getEmail());

        return ResponseEntity.ok(convertUserToDTO(updated));
    }

    /**
     * Asignar roles a un usuario
     */
    @PostMapping("/users/{id}/assign-roles")
    @Transactional
    public ResponseEntity<UserDTO> assignRolesToUser(
            @PathVariable Integer id,
            @Valid @RequestBody AssignRolesToUserDTO dto) throws Exception {

        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Obtener los roles
        List<Role> roles = dto.getRoleIds().stream()
                .map(roleId -> {
                    try {
                        return roleService.findById(roleId);
                    } catch (Exception e) {
                        throw new RuntimeException("Rol no encontrado: " + roleId, e);
                    }
                })
                .collect(Collectors.toList());

        user.setRoles(roles);
        User updated = userService.update(user, id);
        log.info("✓ Roles asignados al usuario: {}", updated.getEmail());

        return ResponseEntity.ok(convertUserToDTO(updated));
    }

    /**
     * Eliminar usuario
     */
    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws Exception {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(id);
        log.info("✓ Usuario eliminado: {}", user.getEmail());

        return ResponseEntity.noContent().build();
    }

    /**
     * Listar usuarios con paginación
     */
    @GetMapping("/users/paginated")
    public ResponseEntity<Page<UserDTO>> paginateUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws Exception {

        Page<User> pageResult = userService.paginar(page, size, "idUser");
        Page<UserDTO> dtoPage = pageResult.map(this::convertUserToDTO);
        return ResponseEntity.ok(dtoPage);
    }

    // ======================= GESTIÓN DE ROLES =======================

    /**
     * Obtener todos los roles
     */
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() throws Exception {
        List<Role> roles = roleService.findAll();
        List<RoleDTO> dtos = roles.stream()
                .map(this::convertRoleToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener rol por ID
     */
    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer id) throws Exception {
        Role role = roleService.findById(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertRoleToDTO(role));
    }

    /**
     * Crear nuevo rol
     */
    @PostMapping("/roles")
    @Transactional
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO dto) throws Exception {
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setModules(List.of()); // Sin módulos inicialmente

        Role saved = roleService.save(role);
        log.info("✓ Rol creado: {}", saved.getName());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdRole())
                .toUri();

        return ResponseEntity.created(location).body(convertRoleToDTO(saved));
    }

    /**
     * Editar rol
     */
    @PutMapping("/roles/{id}")
    @Transactional
    public ResponseEntity<RoleDTO> updateRole(
            @PathVariable Integer id,
            @Valid @RequestBody RoleDTO dto) throws Exception {

        Role role = roleService.findById(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        role.setName(dto.getName());
        role.setDescription(dto.getDescription());

        Role updated = roleService.update(role, id);
        log.info("✓ Rol actualizado: {}", updated.getName());

        return ResponseEntity.ok(convertRoleToDTO(updated));
    }

    /**
     * Asignar módulos a un rol
     */
    @PostMapping("/roles/{id}/assign-modules")
    @Transactional
    public ResponseEntity<RoleDTO> assignModulesToRole(
            @PathVariable Integer id,
            @Valid @RequestBody AssignModulesToRoleDTO dto) throws Exception {

        Role role = roleService.findById(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        // Obtener los módulos
        List<Module> modules = dto.getModuleIds().stream()
                .map(moduleId -> {
                    try {
                        return moduleService.findById(moduleId);
                    } catch (Exception e) {
                        throw new RuntimeException("Módulo no encontrado: " + moduleId, e);
                    }
                })
                .collect(Collectors.toList());

        role.setModules(modules);
        Role updated = roleService.update(role, id);
        log.info("✓ Módulos asignados al rol: {}", updated.getName());

        return ResponseEntity.ok(convertRoleToDTO(updated));
    }

    /**
     * Eliminar rol
     */
    @DeleteMapping("/roles/{id}")
    @Transactional
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) throws Exception {
        Role role = roleService.findById(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        roleService.delete(id);
        log.info("✓ Rol eliminado: {}", role.getName());

        return ResponseEntity.noContent().build();
    }

    // ======================= GESTIÓN DE MÓDULOS =======================

    /**
     * Obtener todos los módulos
     */
    @GetMapping("/modules")
    public ResponseEntity<List<ModuleDTO>> getAllModules() throws Exception {
        List<Module> modules = moduleService.findAll();
        List<ModuleDTO> dtos = modules.stream()
                .map(m -> modelMapper.map(m, ModuleDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener módulo por ID
     */
    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable Integer id) throws Exception {
        Module module = moduleService.findById(id);
        if (module == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(module, ModuleDTO.class));
    }

    /**
     * Crear nuevo módulo
     */
    @PostMapping("/modules")
    @Transactional
    public ResponseEntity<ModuleDTO> createModule(@Valid @RequestBody ModuleDTO dto) throws Exception {
        Module module = modelMapper.map(dto, Module.class);
        Module saved = moduleService.save(module);
        log.info("✓ Módulo creado: {}", saved.getName());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdModule())
                .toUri();

        return ResponseEntity.created(location).body(modelMapper.map(saved, ModuleDTO.class));
    }

    /**
     * Editar módulo
     */
    @PutMapping("/modules/{id}")
    @Transactional
    public ResponseEntity<ModuleDTO> updateModule(
            @PathVariable Integer id,
            @Valid @RequestBody ModuleDTO dto) throws Exception {

        Module module = moduleService.findById(id);
        if (module == null) {
            return ResponseEntity.notFound().build();
        }

        module.setName(dto.getName());
        module.setDescription(dto.getDescription());
        module.setPath(dto.getPath());
        module.setActive(dto.isActive());

        Module updated = moduleService.update(module, id);
        log.info("✓ Módulo actualizado: {}", updated.getName());

        return ResponseEntity.ok(modelMapper.map(updated, ModuleDTO.class));
    }

    /**
     * Eliminar módulo
     */
    @DeleteMapping("/modules/{id}")
    @Transactional
    public ResponseEntity<Void> deleteModule(@PathVariable Integer id) throws Exception {
        Module module = moduleService.findById(id);
        if (module == null) {
            return ResponseEntity.notFound().build();
        }

        moduleService.delete(id);
        log.info("✓ Módulo eliminado: {}", module.getName());

        return ResponseEntity.noContent().build();
    }

    // ======================= HELPER METHODS =======================

    private UserDTO convertUserToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setIdUser(user.getIdUser());
        dto.setEmail(user.getEmail());
        dto.setUserName(user.getUserName());
        dto.setFullName(user.getFullName());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());

        if (user.getRoles() != null) {
            dto.setRoleIds(user.getRoles().stream()
                    .map(Role::getIdRole)
                    .collect(Collectors.toList()));
            dto.setRoles(user.getRoles().stream()
                    .map(this::convertRoleToDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private RoleDTO convertRoleToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setIdRole(role.getIdRole());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());

        if (role.getModules() != null) {
            dto.setModuleIds(role.getModules().stream()
                    .map(Module::getIdModule)
                    .collect(Collectors.toList()));
            dto.setModules(role.getModules().stream()
                    .map(m -> modelMapper.map(m, ModuleDTO.class))
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}

