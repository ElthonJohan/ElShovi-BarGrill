package com.ELShovi.controller;


import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.DeliveryDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.Delivery;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IDeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
//@CrossOrigin(origins = "*")
public class DeliveryController {

    private final IDeliveryService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> findAll() throws Exception{
        List<DeliveryDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        DeliveryDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<DeliveryDTO> save(@Valid @RequestBody DeliveryDTO dto) throws Exception{
        Delivery obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody DeliveryDTO dto) throws Exception{
        Delivery entity = convertToEntity(dto);
        entity.setId(id);
        Delivery obj =  service.update(entity, id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private DeliveryDTO convertToDto(Delivery obj){
        return modelMapper.map(obj, DeliveryDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private Delivery convertToEntity(DeliveryDTO dto){
        return modelMapper.map(dto, Delivery.class);
    }

}