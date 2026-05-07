package com.ELShovi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDTO {
    private Integer idModule;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String path;
    private boolean active = true;
}

