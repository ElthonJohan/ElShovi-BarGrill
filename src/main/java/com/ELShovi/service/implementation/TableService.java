package com.ELShovi.service.implementation;

import com.ELShovi.model.RestaurantTable;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.ITableRepository;
import com.ELShovi.service.ITableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableService extends GenericService<RestaurantTable,Integer> implements ITableService {
    private final ITableRepository repo;
    @Override
    protected IGenericRepository<RestaurantTable,Integer> getRepo() {
        return repo;
    }
}