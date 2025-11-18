package com.ELShovi.repository;

import com.ELShovi.model.Category;

import java.util.List;

public interface ICategoryRepository extends IGenericRepository<Category,Integer> {
    List<Category> findByActiveTrue();

}
