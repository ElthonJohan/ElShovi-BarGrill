package com.ELShovi.service.implementation;

import com.ELShovi.model.Role;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IRoleRepository;
import com.ELShovi.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService extends GenericService<Role,Integer> implements IRoleService {
    private final IRoleRepository repo;
    @Override
    protected IGenericRepository<Role,Integer> getRepo() {
        return repo;
    }
}
