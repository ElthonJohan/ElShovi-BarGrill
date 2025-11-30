package com.ELShovi.controller;


import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.DeliveryDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.Delivery;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IDeliveryService;
import com.ELShovi.service.IOrderService;
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
@RequestMapping("/deliveries")
//@CrossOrigin(origins = "*")
public class DeliveryController {

    private final IDeliveryService service;
    private final IOrderService orderService;

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

        Delivery delivery = convertToEntity(dto);

        Delivery saved = service.save(delivery);

        DeliveryDTO response = convertToDto(saved);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdDelivery())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody DeliveryDTO dto) throws Exception{
        Delivery obj =  service.update(convertToEntity(dto), id);
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
    private Delivery convertToEntity(DeliveryDTO dto) throws Exception{

        Delivery delivery = new Delivery();

        if (dto.getIdDelivery() != null) {
            delivery.setIdDelivery(dto.getIdDelivery());
        }

        // 👇 Asigna la orden usando su ID
        delivery.setOrder(orderService.findById(dto.getIdOrder()));

        delivery.setAddress(dto.getAddress());
        delivery.setPhone(dto.getPhone());
        delivery.setDriverName(dto.getDriverName());
        delivery.setVehiclePlate(dto.getVehiclePlate());
        delivery.setStatus(dto.getStatus());
        delivery.setDeliveryTime(dto.getDeliveryTime());

        return delivery;
    }

    //Metodo para la paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<DeliveryDTO>> paginar (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idDelivery") String sortBy) throws Exception {
        Page<Delivery> pageResult = service.paginar(page, size, sortBy);
        Page<DeliveryDTO> dtoPage = pageResult.map(this::convertToDto);
        return  ResponseEntity.ok(dtoPage);
    }
}