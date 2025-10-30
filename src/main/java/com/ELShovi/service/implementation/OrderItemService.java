package com.ELShovi.service.implementation;

import com.ELShovi.model.OrderItem;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IOrderItemRepository;
import com.ELShovi.service.IGenericService;
import com.ELShovi.service.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService extends GenericService<OrderItem,Integer> implements IOrderItemService {

    private final IOrderItemRepository repo;

    @Override
    protected IGenericRepository<OrderItem, Integer> getRepo() {
        return repo;
    }
}
