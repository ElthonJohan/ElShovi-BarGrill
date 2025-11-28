package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.TableDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.Table;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.ITableService;
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
@RequiredArgsConstructor
@RequestMapping("/tables")
//@CrossOrigin(origins = "*")
public class TableController {

    private final ITableService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<TableDTO>> findAll() throws Exception{
        List<TableDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        TableDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<TableDTO> save(@Valid @RequestBody TableDTO dto) throws Exception{
        Table obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTable()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody TableDTO dto) throws Exception{
        Table obj = service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private TableDTO convertToDto(Table obj){
        return modelMapper.map(obj, TableDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private Table convertToEntity(TableDTO dto){
        return modelMapper.map(dto, Table.class);
    }

}