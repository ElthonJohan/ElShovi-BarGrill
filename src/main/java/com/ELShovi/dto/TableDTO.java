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
public class TableDTO {
    private Integer idTable;
    @NotNull
    private int tableNumber;
    @NotNull
    private int capacity;
    @NotNull
    private String status;
}
