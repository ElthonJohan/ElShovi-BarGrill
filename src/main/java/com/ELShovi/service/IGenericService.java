package com.ELShovi.service;

import java.util.List;

public interface IGenericService <T,ID> {
    T save(T entity) throws Exception;
    T update(T entity, ID id) throws Exception;
    List<T> findAll() throws Exception;
    T findById(ID id) throws Exception;
    void delete(ID id) throws Exception;
}