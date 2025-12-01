package com.ELShovi.dto;

import com.ELShovi.model.enums.OrderStatus;
import com.ELShovi.model.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"userName", "tableNumber"})
public class OrderDTO {
    private Integer idOrder;
    @NotNull
    private Integer idUser;
    private Integer idTable;

    private String userName;     // 👈 NUEVO
    private Integer tableNumber;


    private OrderType orderType;
    @NotNull
    private OrderStatus status;
    private double totalAmount;
    private String notes;
    private LocalDateTime createdAt=LocalDateTime.now();
    private List<OrderItemDTO> items;
    private Integer idPayment;
}