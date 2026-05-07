package com.ELShovi.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer idRole;
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    private String description;
    private List<Integer> moduleIds; // IDs de módulos asignados a este rol
    private List<ModuleDTO> modules; // Módulos con detalles (para respuestas)
}
