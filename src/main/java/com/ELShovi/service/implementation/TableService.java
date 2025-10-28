package com.ELShovi.service.implementation;

import com.ELShovi.model.Table;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.ITableRepository;
import com.ELShovi.service.ITableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableService extends GenericService<Table,Integer> implements ITableService {
    private final ITableRepository repo;
    @Override
    protected IGenericRepository<Table,Integer> getRepo() {
        return repo;
    }
}