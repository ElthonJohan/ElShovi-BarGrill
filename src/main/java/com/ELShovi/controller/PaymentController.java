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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPayment()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PaymentDTO dto) throws Exception{
        Payment obj =  service.update(convertToEntity(dto), id);
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

    //Metodo para la paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<PaymentDTO>> paginar (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPayment") String sortBy) throws Exception {
        Page<Payment> pageResult = service.paginar(page, size, sortBy);
        Page<PaymentDTO> dtoPage = pageResult.map(this::convertToDto);
        return  ResponseEntity.ok(dtoPage);
    }

}