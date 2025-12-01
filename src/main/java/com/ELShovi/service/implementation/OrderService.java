package com.ELShovi.service.implementation;

import com.ELShovi.dto.OrderDTO;
import com.ELShovi.model.*;
import com.ELShovi.model.enums.OrderStatus;
import com.ELShovi.model.enums.OrderType;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IOrderItemRepository;
import com.ELShovi.repository.IOrderRepository;
import com.ELShovi.service.IGenericService;
import com.ELShovi.service.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService extends GenericService<Order, Integer> implements IOrderService {

    private final IOrderRepository repo;
    private final ModelMapper orderMapper;


    @Override
    protected IGenericRepository<Order, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional
    public Order save(Order order) {

        if (order.getOrderType() == OrderType.EN_MESA) {

            Integer idMesa = order.getTable().getIdTable();

            // Buscar si hay otra orden activa en esa mesa
            List<Order> pendientes = repo.findActiveOrdersByTable(idMesa, order.getIdOrder());

            if (!pendientes.isEmpty()) {
                throw new RuntimeException("La mesa seleccionada ya tiene una orden activa.");
            }
        }


        return repo.save(order);  // cascade guarda OrderItems también
    }

    public boolean mesaOcupada(Integer idMesa) {
        return repo.existsByTableAndStatusNot(idMesa, "COMPLETADA");
    }


    @Override
    @Transactional
    public Order update(Order order, Integer id) throws Exception {
        if (order.getOrderType() == OrderType.EN_MESA) {

            Integer idMesa = order.getTable().getIdTable();

            // Buscar si hay otra orden activa en esa mesa
            List<Order> pendientes = repo.findActiveOrdersByTable(idMesa, order.getIdOrder());

            if (!pendientes.isEmpty()) {
                throw new RuntimeException("La mesa seleccionada ya tiene una orden activa.");
            }
        }


        Order original = repo.findById(id)
                .orElseThrow(() -> new Exception("Order not found " + id));

        // COPIAR CAMPOS BÁSICOS
        original.setUser(order.getUser());
        original.setTable(order.getTable());
        original.setOrderType(order.getOrderType());
        original.setStatus(order.getStatus());
        original.setNotes(order.getNotes());
        original.setPayment(order.getPayment());
        original.setTotalAmount(order.getTotalAmount());

        // REEMPLAZAR ITEMS
        original.getItems().clear();
        order.getItems().forEach(item -> {
            item.setOrder(original);
            original.getItems().add(item);
        });

        return repo.save(original);
    }
    public OrderDTO createFromDto(OrderDTO dto) {
        dto.setStatus(OrderStatus.PENDIENTE);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setOrderType(OrderType.DELIVERY);

        User user = new User();
        user.setIdUser(dto.getIdUser());

        Table table = new Table();
        table.setIdTable(dto.getIdTable()); // SIEMPRE 1

        Order order = new Order();
        order.setUser(user);
        order.setTable(table);
        order.setOrderType(dto.getOrderType());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotalAmount());
        order.setNotes(dto.getNotes());
        order.setCreatedAt(dto.getCreatedAt());

        List<OrderItem> items = dto.getItems().stream().map(i -> {
            OrderItem item = new OrderItem();

            MenuItem mi = new MenuItem();
            mi.setIdMenuItem(i.getIdMenuItem());
            item.setMenuItem(mi);

            item.setQuantity(i.getQuantity());
            item.setUnitPrice(i.getUnitPrice());
            item.setOrder(order);
            return item;
        }).toList();

        order.setItems(items);

        Order saved = repo.save(order);
        return orderMapper.map(saved, OrderDTO.class);
    }

}
