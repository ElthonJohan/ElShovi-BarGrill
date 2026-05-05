package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.MenuItemDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.MenuItem;
import com.ELShovi.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/categories")
//@CrossOrigin(origins = "*")


public class CategoryController {

    private final ICategoryService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('administrador','mesero','cliente')")
    public ResponseEntity<List<CategoryDTO>> findAll() throws Exception{
        List<CategoryDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    // 🔹 Activas
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('administrador','mesero','cliente')")
    public ResponseEntity<List<CategoryDTO>> getActive() {
        List<CategoryDTO> listActive = service.findActive().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)

        return ResponseEntity.ok(listActive);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('administrador','mesero','cliente')")
    public ResponseEntity<CategoryDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        CategoryDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto) throws Exception{
        Category obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdCategory()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<CategoryDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody CategoryDTO dto) throws Exception{
        Category obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('administrador')")
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

    //Metodo para la paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<CategoryDTO>> paginar (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCategory") String sortBy) throws Exception {
        Page<Category> pageResult = service.paginar(page, size, sortBy);
        Page<CategoryDTO> dtoPage = pageResult.map(this::convertToDto);
        return  ResponseEntity.ok(dtoPage);
    }

}