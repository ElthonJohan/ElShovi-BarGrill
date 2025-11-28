package com.ELShovi.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


//import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer idRole;
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    private String description;
}
