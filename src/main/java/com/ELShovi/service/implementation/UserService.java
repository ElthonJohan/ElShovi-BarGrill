package com.ELShovi.service.implementation;

import com.ELShovi.dto.ProfileDTO;
import com.ELShovi.model.Role;
import com.ELShovi.model.User;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IRoleRepository;
import com.ELShovi.repository.IUserRepository;
import com.ELShovi.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends GenericService<User,Integer> implements IUserService {

    private final IUserRepository repo;
    private final IRoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected IGenericRepository<User,Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional
    public User save(User user) throws Exception {
        // Validar y resolver roles a entidades gestionadas
        List<Role> incoming = user.getRoles();
        if (incoming == null || incoming.isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un role para crear un usuario");
        }

        List<Role> managedRoles = new ArrayList<>();
        for (Role r : incoming) {
            Integer roleId = r.getIdRole(); // o r.getId() si así lo tienes
            Role roleEntity = roleRepo.findById(roleId)
                    .orElseThrow(() -> new Exception("Role no encontrado id=" + roleId));
            managedRoles.add(roleEntity);
        }

        // Asignar la lista de entidades gestionadas
        user.setRoles(managedRoles);

        // Encriptar antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public User update(User request,Integer id) throws Exception {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar campos permitidos
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setFullName(request.getFullName());

        // Estado (activo/inactivo)
        user.setActive(request.isActive());

        // Roles (si vienen en el request)
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            user.setRoles(request.getRoles());
        }

        // 🔥 Si la contraseña viene vacía, NO cambiarla
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return repo.save(user);

    }

    public User updateProfile(Integer idUser, ProfileDTO dto) {

        User user = repo.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getUserName() != null) {
            user.setUserName(dto.getUserName());
        }

        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return repo.save(user);
    }

}