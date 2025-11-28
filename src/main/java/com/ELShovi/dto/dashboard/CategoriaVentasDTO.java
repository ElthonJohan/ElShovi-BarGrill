package com.ELShovi.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaVentasDTO {
    private String categoria;
    private Long cantidadVendida;
}

