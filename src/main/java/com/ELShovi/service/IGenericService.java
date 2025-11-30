package com.ELShovi.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IGenericService <T,ID> {
    T save(T entity) throws Exception;
    T update(T entity, ID id) throws Exception;
    List<T> findAll() throws Exception;
    T findById(ID id) throws Exception;
    void delete(ID id) throws Exception;

    // Añadiendo la paginación
    Page<T> paginar(int page, int size, String sortBy) throws Exception;
}