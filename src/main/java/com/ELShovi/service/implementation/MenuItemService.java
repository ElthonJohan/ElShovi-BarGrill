package com.ELShovi.service.implementation;

import com.ELShovi.model.MenuItem;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IMenuItemRepository;
import com.ELShovi.service.IMenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemService extends GenericService<MenuItem,Integer> implements IMenuItemService {

    private final IMenuItemRepository repo;

    @Override
    protected IGenericRepository<MenuItem, Integer> getRepo() {
        return repo;
    }
}
