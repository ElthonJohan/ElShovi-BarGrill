package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.OrderItemDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.OrderItem;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IOrderItemService;
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
@RequestMapping("/order-items")
//@CrossOrigin(origins = "*")
public class OrderItemController {

    private final IOrderItemService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> findAll() throws Exception{
        List<OrderItemDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        OrderItemDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> save(@Valid @RequestBody OrderItemDTO dto) throws Exception{
        OrderItem obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdOrderItem()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody OrderItemDTO dto) throws Exception{
        OrderItem obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private OrderItemDTO convertToDto(OrderItem obj){
        return modelMapper.map(obj, OrderItemDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private OrderItem convertToEntity(OrderItemDTO dto){
        return modelMapper.map(dto, OrderItem.class);
    }

}
