package com.ELShovi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private Integer idCategory;
    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    private String description;
    @NotNull
    private boolean active;
}
