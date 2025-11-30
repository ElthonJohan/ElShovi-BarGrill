package com.ELShovi.repository;

import com.ELShovi.model.MenuItem;

import java.util.List;

public interface IMenuItemRepository extends IGenericRepository<MenuItem,Integer> {

    List<MenuItem> findByActiveTrue();

    List<MenuItem> findByCategory_IdCategory(Integer idCategory);

    List<MenuItem> findByNameContainingIgnoreCase(String name);

}
