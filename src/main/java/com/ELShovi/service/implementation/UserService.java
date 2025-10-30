package com.ELShovi.service.implementation;

import com.ELShovi.model.User;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IUserRepository;
import com.ELShovi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends GenericService<User,Integer> implements IUserService {
    private final IUserRepository repo;
    @Override
    protected IGenericRepository<User,Integer> getRepo() {
        return repo;
    }
}