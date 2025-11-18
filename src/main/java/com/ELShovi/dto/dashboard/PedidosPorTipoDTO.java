package com.ELShovi.dto.dashboard;

import com.ELShovi.model.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class PedidosPorTipoDTO {
    private OrderType tipo;
    private Long total;
    // Constructor usado por JPQL
    public PedidosPorTipoDTO(OrderType tipo, Long total) {
        this.tipo = tipo;
        this.total = total;
    }

    // Constructor vacío (obligatorio)
    public PedidosPorTipoDTO() {}
}

