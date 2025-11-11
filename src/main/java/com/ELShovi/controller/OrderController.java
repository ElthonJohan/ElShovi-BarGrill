package com.ELShovi.controller;


import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.OrderDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.Order;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IOrderService;
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
@CrossOrigin(origins = "http://localhost:4200")

@RequiredArgsConstructor
@RequestMapping("/orders")
//@CrossOrigin(origins = "*")
public class OrderController {

    private final IOrderService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() throws Exception{
        List<OrderDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        OrderDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody OrderDTO dto) throws Exception{
        Order obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdOrder()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody OrderDTO dto) throws Exception{
        Order obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private OrderDTO convertToDto(Order obj){
        return modelMapper.map(obj, OrderDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private Order convertToEntity(OrderDTO dto){
        return modelMapper.map(dto, Order.class);
    }

}
