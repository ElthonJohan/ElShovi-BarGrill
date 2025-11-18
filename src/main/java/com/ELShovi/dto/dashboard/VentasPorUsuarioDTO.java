package com.ELShovi.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasPorUsuarioDTO {
    private String usuario;
    private Double total;
}

