package com.ELShovi.controller;


import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.MenuItemDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.MenuItem;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IMenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
//@PreAuthorize("hasRole('administrador')")

@RequiredArgsConstructor
@RequestMapping("/menu-items")
//@CrossOrigin(origins = "*")
public class MenuItemController {

    private final IMenuItemService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> findAll() throws Exception{
        List<MenuItemDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }



    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        MenuItemDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> save(@Valid @RequestBody MenuItemDTO dto) throws Exception{
        MenuItem obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMenuItem()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody MenuItemDTO dto) throws Exception{
        MenuItem obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Filtros personalizados

    // 🔹 Solo productos activos
    @GetMapping("/active")
    public ResponseEntity<List<MenuItemDTO>> getActive() {
        List<MenuItemDTO> listActive = service.findActive().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)

        return ResponseEntity.ok( listActive);
    }

    // 🔹 Buscar productos por categoría
    @GetMapping("/category/{id}")
    public ResponseEntity<List<MenuItemDTO>> getByCategory(@PathVariable Integer id) {
        List<MenuItemDTO> listByCategory = service.findByCategory(id).stream().map(this::convertToDto).toList(); // e -> convertToDto(e)

        return ResponseEntity.ok(listByCategory);
    }

    // Convertir de un Modelo a un DTO
    private MenuItemDTO convertToDto(MenuItem obj){
        return modelMapper.map(obj, MenuItemDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private MenuItem convertToEntity(MenuItemDTO dto){
        return modelMapper.map(dto, MenuItem.class);
    }

}
