package com.ELShovi.controller;

import com.ELShovi.dto.TableDTO;
import com.ELShovi.model.RestaurantTable;
import com.ELShovi.service.ITableService;
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
@RequestMapping("/tables")


//@CrossOrigin(origins = "*")
public class TableController {

    private final ITableService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','mesero')")
    public ResponseEntity<List<TableDTO>> findAll() throws Exception{
        List<TableDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','mesero')")
    public ResponseEntity<TableDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        TableDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<TableDTO> save(@Valid @RequestBody TableDTO dto) throws Exception{
        RestaurantTable obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTable()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TableDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody TableDTO dto) throws Exception{
        RestaurantTable obj = service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private TableDTO convertToDto(RestaurantTable obj){
        return modelMapper.map(obj, TableDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private RestaurantTable convertToEntity(TableDTO dto){
        return modelMapper.map(dto, RestaurantTable.class);
    }

    //Metodo para la paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<TableDTO>> paginar (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idTable") String sortBy) throws Exception {
        Page<RestaurantTable> pageResult = service.paginar(page, size, sortBy);
        Page<TableDTO> dtoPage = pageResult.map(this::convertToDto);
        return  ResponseEntity.ok(dtoPage);
    }

}