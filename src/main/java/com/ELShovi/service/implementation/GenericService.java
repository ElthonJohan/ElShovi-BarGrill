package com.ELShovi.service.implementation;

import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.service.IGenericService;

import java.util.List;


public abstract class GenericService<T,ID> implements IGenericService<T,ID> {

    protected abstract IGenericRepository<T,ID> getRepo();

    @Override
    public T save(T entity) throws Exception {
        return getRepo().save(entity);
    }

    @Override
    public T update(T entity, ID id) throws Exception {
        getRepo().findById(id).orElseThrow(()-> new Exception("ID NOT FOUND: " + id));
        return getRepo().save(entity);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(()-> new Exception("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().findById(id).orElseThrow(()->new Exception("ID NOT FOUND: " + id));
        getRepo().deleteById(id);
    }
}
