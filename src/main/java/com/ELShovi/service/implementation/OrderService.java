package com.ELShovi.service.implementation;

import com.ELShovi.model.Order;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IOrderItemRepository;
import com.ELShovi.repository.IOrderRepository;
import com.ELShovi.service.IGenericService;
import com.ELShovi.service.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService extends GenericService<Order, Integer> implements IOrderService {

    private final IOrderRepository repo;

    @Override
    protected IGenericRepository<Order, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        return repo.save(order);  // cascade guarda OrderItems también
    }

    @Override
    @Transactional
    public Order update(Order order, Integer id) throws Exception {

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
}
