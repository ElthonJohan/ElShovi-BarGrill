package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.RoleDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.Role;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")

//@CrossOrigin(origins = "*")
public class RoleController {

    private final IRoleService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() throws Exception{
        List<RoleDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        RoleDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> save(@Valid @RequestBody RoleDTO dto) throws Exception{
        Role obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdRole()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody RoleDTO dto) throws Exception{
        Role obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private RoleDTO convertToDto(Role obj){
        return modelMapper.map(obj, RoleDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto, Role.class);
    }

    //Metodo para la paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<RoleDTO>> paginar (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCategory") String sortBy) throws Exception {
        Page<Role> pageResult = service.paginar(page, size, sortBy);
        Page<RoleDTO> dtoPage = pageResult.map(this::convertToDto);
        return  ResponseEntity.ok(dtoPage);
    }

}