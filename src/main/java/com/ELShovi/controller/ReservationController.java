package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.ReservationDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.Reservation;
import com.ELShovi.model.Table;
import com.ELShovi.model.User;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequiredArgsConstructor
@RequestMapping("/reservations")
//@CrossOrigin(origins = "*")
public class ReservationController {

    private final IReservationService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> findAll() throws Exception{
        List<ReservationDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        ReservationDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> save(@Valid @RequestBody ReservationDTO dto) throws Exception{
        Reservation obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdReservation()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ReservationDTO dto) throws Exception{
        Reservation obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Convertir de un Modelo a un DTO
    private ReservationDTO convertToDto(Reservation obj){
        return modelMapper.map(obj, ReservationDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private Reservation convertToEntity(ReservationDTO dto){ return modelMapper.map(dto, Reservation.class); }

    //Metodo para la paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<ReservationDTO>> paginar (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idReservation") String sortBy) throws Exception {
        Page<Reservation> pageResult = service.paginar(page, size, sortBy);
        Page<ReservationDTO> dtoPage = pageResult.map(this::convertToDto);
        return  ResponseEntity.ok(dtoPage);
    }

}