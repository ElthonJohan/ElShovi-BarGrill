package com.ELShovi.service.implementation;

import com.ELShovi.model.Order;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IOrderItemRepository;
import com.ELShovi.repository.IOrderRepository;
import com.ELShovi.service.IGenericService;
import com.ELShovi.service.IOrderService;
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
}
