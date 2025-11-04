package com.ELShovi.dto;

import com.ELShovi.model.enums.OrderStatus;
import com.ELShovi.model.enums.OrderType;
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
    private int id;
    @NotNull
    private int userId;
    @NotNull
    private int tableId;
    @NotNull
    private OrderType orderType;
    @NotNull
    private OrderStatus status;
    @NotNull
    private double totalAmount;
    private String notes;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
    @NotNull
    private int paymentId;
}