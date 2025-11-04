package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.PaymentDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.Payment;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IPaymentService;
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
@RequestMapping("/payments")
//@CrossOrigin(origins = "*")
public class PaymentController {

    private final IPaymentService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> findAll() throws Exception{
        List<PaymentDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        PaymentDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> save(@Valid @RequestBody PaymentDTO dto) throws Exception{
        Payment obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PaymentDTO dto) throws Exception{
        Payment entity = convertToEntity(dto);
        entity.setId(id);
        Payment obj =  service.update(entity, id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private PaymentDTO convertToDto(Payment obj){
        return modelMapper.map(obj, PaymentDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private Payment convertToEntity(PaymentDTO dto){return modelMapper.map(dto, Payment.class);
    }

}