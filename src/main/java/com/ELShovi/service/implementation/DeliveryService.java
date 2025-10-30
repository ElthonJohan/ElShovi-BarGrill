package com.ELShovi.service.implementation;

import com.ELShovi.model.Category;
import com.ELShovi.model.Delivery;
import com.ELShovi.repository.IDeliveryRepository;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.service.IDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService extends GenericService<Delivery,Integer> implements IDeliveryService {

    private final IDeliveryRepository repo;

    @Override
    protected IGenericRepository<Delivery, Integer> getRepo() {
        return repo;
    }

}
