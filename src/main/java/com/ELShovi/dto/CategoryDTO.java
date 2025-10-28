package com.ELShovi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String description;
}
