package com.ELShovi.controller;


import com.ELShovi.dto.*;
import com.ELShovi.model.Order;
import com.ELShovi.model.OrderItem;
import com.ELShovi.model.Payment;
import com.ELShovi.model.enums.OrderStatus;
import com.ELShovi.model.enums.OrderType;
import com.ELShovi.model.enums.PaymentMethod;
import com.ELShovi.model.enums.PaymentStatus;
import com.ELShovi.repository.IOrderRepository;
import com.ELShovi.service.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequiredArgsConstructor
@RequestMapping("/orders")
//@CrossOrigin(origins = "*")!
@PreAuthorize("hasAnyRole('ADMIN','mesero','cliente')")

public class OrderController {
    private final IOrderService service;
    private final IOrderRepository repo;

    private final IUserService userService;
    private final ITableService tableService;
    private final IMenuItemService menuItemService;
    private final IPaymentService paymentService;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() throws Exception{
        List<OrderDTO> list = service.findAll().stream().map(this::convertToDto).toList(); // e -> convertToDto(e)
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO>  findById(@PathVariable("id") Integer id) throws Exception{
        OrderDTO obj = convertToDto(service.findById(id)) ;
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/checkMesa/{idMesa}")
    public ResponseEntity<Boolean> checkMesa(
            @PathVariable Integer idMesa,
            @RequestParam(required = false) Integer exclude) {

        List<Order> pendientes = repo.findActiveOrdersByTable(idMesa, exclude);
        return ResponseEntity.ok(pendientes.isEmpty());
    }

    @GetMapping("/mesa/ocupada/{idMesa}")
    public ResponseEntity<Boolean> mesaOcupada(
            @PathVariable Integer idMesa) {

        boolean ocupada = service.mesaOcupada(idMesa);
        return ResponseEntity.ok(ocupada);
    }



    @PostMapping
    @Transactional
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody OrderDTO dto) throws Exception{
        Order order = convertToEntity(dto);
        order.setStatus(OrderStatus.PENDIENTE);

        // ==============================
        //  PAGO AUTOMÁTICO PARA DELIVERY
        // ==============================
        if (dto.getOrderType() == OrderType.DELIVERY) {

            Payment payment = new Payment();
            payment.setAmount(order.getTotalAmount());
            payment.setPaymentMethod(PaymentMethod.ONLINE);
            payment.setStatus(PaymentStatus.COMPLETADO);

            // Guardar el pago
            paymentService.save(payment);
            // Asignarlo a la orden
            order.setPayment(payment);

            // la orden queda automáticamente pagada
            order.setStatus(OrderStatus.COMPLETADA);
        }

        // ==============================
        //  B. Pago manual si el frontend envía idPayment
        // ==============================
        else if (dto.getIdPayment() != null && dto.getIdPayment() > 0) {
            order.setPayment(paymentService.findById(dto.getIdPayment()));
        } else {
            order.setPayment(null);
        }

        // ==============================
        // 4️⃣ Guardar orden
        // ==============================
        Order saved = service.save(order);

        // 5️⃣ Convertir Entity → DTO (respuesta)
        OrderDTO response = convertToDto(saved);

        // 6️⃣ Construir URL de recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdOrder())
                .toUri();

        // 7️⃣ Devolver DTO completo
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/{id}/pay")
    @Transactional
    public ResponseEntity<?> manualPayment(
            @PathVariable Integer id,
            @RequestBody PaymentRequestDTO dto) throws Exception {

        Order order = service.findById(id);



        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Orden no encontrada"));
        }

        if (order.getPayment() != null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "La orden ya fue pagada"));
        }


        // Crear pago
        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.COMPLETADO);

        paymentService.save(payment);

        // Asociar pago a la orden y actualizar estado
        order.setPayment(payment);
        order.setStatus(OrderStatus.COMPLETADA);

        service.save(order);

        // Respuesta JSON
        PaymentDTO response = new PaymentDTO(
                payment.getIdPayment(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getStatus(),
                "Pago registrado correctamente"
        );

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<OrderDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody OrderDTO dto) throws Exception{
        Order obj =  service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo para la paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<OrderDTO>> paginar (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idOrder") String sortBy) throws Exception {
        Page<Order> pageResult = service.paginar(page, size, sortBy);
        Page<OrderDTO> dtoPage = pageResult.map(this::convertToDto);
        return  ResponseEntity.ok(dtoPage);
    }

    // ================== MAPPERS ==================

    // DTO → ENTITY (para guardar / actualizar)
    private Order convertToEntity(OrderDTO dto) throws  Exception {

        Order order = new Order();


        // ID solo si viene (solo en PUT)
        if (dto.getIdOrder() != null) {
            order.setIdOrder(dto.getIdOrder());
        }
        // USER
        order.setUser(userService.findById(dto.getIdUser()));

        // TABLE (puede ser null si es DELIVERY)
        if (dto.getOrderType() == OrderType.DELIVERY) {
            order.setRestaurantTable(null);
        } else if (dto.getIdTable() != null) {
            order.setRestaurantTable(tableService.findById(dto.getIdTable()));
        }

        order.setOrderType(dto.getOrderType());
        if (dto.getStatus() != null) {
            order.setStatus(dto.getStatus());
        }
        order.setNotes(dto.getNotes());
        order.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());

        // PAYMENT (puede venir null, vacío o con id)
        if (dto.getIdPayment() != null && dto.getIdPayment() > 0) {
            order.setPayment(paymentService.findById(dto.getIdPayment()));
        } else {
            order.setPayment(null);
        }

        // ITEMS
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : dto.getItems()) {
            OrderItem item = new OrderItem();

            if (itemDTO.getIdOrderItem() != null) {
                item.setIdOrderItem(itemDTO.getIdOrderItem());
            }

            item.setMenuItem(menuItemService.findById(itemDTO.getIdMenuItem()));
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setOrder(order);

            total = total.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            items.add(item);
        }

        order.setItems(items);
        order.setTotalAmount(total); // 🔥 se recalcula siempre

        return order;
    }

    // ENTITY → DTO (para respuestas al frontend)
    private OrderDTO convertToDto(Order order) {

        OrderDTO dto = new OrderDTO();
        dto.setIdOrder(order.getIdOrder());
        dto.setIdUser(order.getUser().getIdUser());
        dto.setUserName(order.getUser().getUserName());

        // Mesa / TableNumber
        if (order.getOrderType() == OrderType.LLEVAR) {
            dto.setIdTable(null);
            dto.setTableNumber(-1); // 👈 AQUÍ
        } else {
            dto.setIdTable(
                    order.getRestaurantTable() != null ? order.getRestaurantTable().getIdTable() : null
            );

            dto.setTableNumber(
                    order.getRestaurantTable() != null ? order.getRestaurantTable().getTableNumber() : null
            );
        }
        dto.setPaymentMethod(order.getPayment() != null ?
                order.getPayment().getPaymentMethod() : null);

        dto.setOrderType(order.getOrderType());
        dto.setStatus(order.getStatus());
        dto.setNotes(order.getNotes());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setTotalAmount(order.getTotalAmount().doubleValue());
        dto.setIdPayment(order.getPayment() != null ? order.getPayment().getIdPayment() : null);

        List<OrderItemDTO> items = order.getItems()
                .stream()
                .map(oi -> {
                    OrderItemDTO i = new OrderItemDTO();
                    i.setIdOrderItem(oi.getIdOrderItem());
                    i.setIdMenuItem(oi.getMenuItem().getIdMenuItem());
                    i.setQuantity(oi.getQuantity());
                    i.setUnitPrice(oi.getUnitPrice());
                    return i;
                })
                .toList();

        dto.setItems(items);

        return dto;
    }

}
