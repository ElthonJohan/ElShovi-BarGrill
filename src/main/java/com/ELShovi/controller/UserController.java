package com.ELShovi.controller;

import com.ELShovi.dto.CategoryDTO;
import com.ELShovi.dto.UserDTO;
import com.ELShovi.model.Category;
import com.ELShovi.model.User;
import com.ELShovi.service.ICategoryService;
import com.ELShovi.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")

@RequiredArgsConstructor
@RequestMapping("/users")
//@CrossOrigin(origins = "*")
public class UserController {

    private final IUserService service;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() throws Exception{
        List<UserDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        UserDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO dto) throws Exception{

        User obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdUser()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody UserDTO dto) throws Exception{
        User obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir de un Modelo a un DTO
    private UserDTO convertToDto(User obj){
        return modelMapper.map(obj, UserDTO.class);
    }

    // Convertir de un DTO a un Modelo (Entity)
    private User convertToEntity(UserDTO dto){
        return modelMapper.map(dto, User.class);
    }

}