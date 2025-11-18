package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.model.Category;
import com.ELShovi.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('administrador')")
@RequiredArgsConstructor
@RequestMapping("/categories")
//@CrossOrigin(origins = "*")
public class CategoryController {

    private final ICategoryService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() throws Exception{
        List<CategoryDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    // 🔹 Activas
    @GetMapping("/active")
    public ResponseEntity<List<CategoryDTO>> getActive() {
        List<CategoryDTO> listActive = service.findActive().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)

        return ResponseEntity.ok(listActive);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        CategoryDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto) throws Exception{
        Category obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdCategory()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody CategoryDTO dto) throws Exception{
        Category obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private CategoryDTO convertToDto(Category obj){
        return modelMapper.map(obj, CategoryDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private Category convertToEntity(CategoryDTO dto){
        return modelMapper.map(dto, Category.class);
    }

}