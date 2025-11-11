package com.ELShovi.service.implementation;

import com.ELShovi.model.User;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IUserRepository;
import com.ELShovi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends GenericService<User,Integer> implements IUserService {
    private final IUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected IGenericRepository<User,Integer> getRepo() {
        return repo;
    }

    @Override
    public User save(User user) throws Exception {
        // Encriptar antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }
}