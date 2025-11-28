package com.ELShovi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {
    private Integer idMenuItem;
    @NotNull
    @Size(min = 3, max = 150)
    private String name;
    private String description;
    @NotNull
    private double price;
    private String imageUrl;
    @NotNull
    private boolean active;
    @NotNull
    private int idCategory;
}