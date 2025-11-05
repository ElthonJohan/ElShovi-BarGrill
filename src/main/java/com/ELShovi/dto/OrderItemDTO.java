package com.ELShovi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

// import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Integer idOrderItem;
    @NotNull
    private Integer idMenuItem;
    @NotNull
    private int quantity;
    @NotNull
    private double unitPrice;
    @NotNull
    private Integer idOrder;
}
