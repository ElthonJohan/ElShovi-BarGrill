package com.ELShovi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para crear un nuevo usuario con roles asignados
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotNull
    @Email
    private String email;
    
    @NotNull
    @Size(min = 3, max = 50)
    private String userName;
    
    @NotNull
    @Size(min = 8, max = 50)
    private String password;
    
    private String fullName;
    
    @NotNull
    private List<Integer> roleIds; // IDs de los roles a asignar
}

