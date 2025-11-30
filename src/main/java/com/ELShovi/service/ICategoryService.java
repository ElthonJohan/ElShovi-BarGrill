package com.ELShovi.service;

import com.ELShovi.model.Category;

import java.util.List;

public interface ICategoryService extends IGenericService<Category,Integer>{
    List<Category> findActive();

}
