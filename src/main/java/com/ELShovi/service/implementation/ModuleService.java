package com.ELShovi.service.implementation;

import com.ELShovi.model.Module;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IModuleRepository;
import com.ELShovi.service.IModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleService extends GenericService<Module, Integer> implements IModuleService {
    private final IModuleRepository repo;

    @Override
    protected IGenericRepository<Module, Integer> getRepo() {
        return repo;
    }
}

