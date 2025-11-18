package com.ELShovi.dto;

import com.ELShovi.model.enums.OrderStatus;
import com.ELShovi.model.enums.OrderType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


//import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer idOrder;
    @NotNull
    private Integer idUser;
    @NotNull
    private Integer idTable;
    @NotNull
    private OrderType orderType;
    @NotNull
    private OrderStatus status;
    @NotNull
    private double totalAmount;
    @Size(min = 1, max = 200)
    private String notes;
    private LocalDateTime createdAt=LocalDateTime.now();
    @NotNull
    private List<OrderItemDTO> items;
    @NotNull
    private int idPayment;
}