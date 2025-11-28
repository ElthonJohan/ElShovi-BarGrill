package com.ELShovi.service.implementation;

import com.ELShovi.model.Category;
import com.ELShovi.repository.ICategoryRepository;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService extends GenericService<Category,Integer> implements ICategoryService {
    private final ICategoryRepository repo;
    @Override
    protected IGenericRepository<Category, Integer> getRepo() {
        return repo;
    }


    @Override
    public List<Category> findActive() {
        return repo.findByActiveTrue()
                .stream().map(c ->
                        new Category(c.getIdCategory(), c.getName(), c.getDescription(), c.isActive()))
                .toList();
    }
}
