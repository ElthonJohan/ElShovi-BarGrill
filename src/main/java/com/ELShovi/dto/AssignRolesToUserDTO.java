package com.ELShovi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para asignar roles a un usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRolesToUserDTO {
    @NotNull
    private Integer userId;
    
    @NotNull
    private List<Integer> roleIds; // IDs de los roles a asignar
}

