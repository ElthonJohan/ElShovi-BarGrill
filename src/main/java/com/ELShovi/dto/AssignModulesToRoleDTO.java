package com.ELShovi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para asignar módulos a un rol
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignModulesToRoleDTO {
    @NotNull
    private Integer roleId;
    
    @NotNull
    private List<Integer> moduleIds; // IDs de los módulos a asignar
}

