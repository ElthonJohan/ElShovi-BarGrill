package com.ELShovi.service;

import com.ELShovi.dto.MenuItemDTO;
import com.ELShovi.model.MenuItem;

import java.util.List;

public interface IMenuItemService extends IGenericService<MenuItem,Integer> {

    List<MenuItem> findActive();

    List<MenuItem> findByCategory(Integer categoryId);
}
