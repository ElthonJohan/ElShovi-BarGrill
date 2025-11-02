package com.ELShovi.config;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean(name = "defaultMapper")
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }

    //@Bean(name = "categoryMapper")
//    public ModelMapper categoryMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        //Escritura
//        modelMapper.createTypeMap(CategoryDTO.class, Category.class)
//                .addMapping(CategoryDTO::getName, (dest, v) -> dest.setName((String) v))
//                .addMapping(CategoryDTO::getDescription, (dest, v) -> dest.setDescription((String) v));
//
//        //Lectura
//        modelMapper.createTypeMap(Category.class, CategoryDTO.class)
//                .addMapping(Category::getName, (dest, v) -> dest.setName((String) v))
//                .addMapping(Category::getDescription, (dest, v) -> dest.setDescription((String) v));
//
//        return modelMapper;
//    }
}