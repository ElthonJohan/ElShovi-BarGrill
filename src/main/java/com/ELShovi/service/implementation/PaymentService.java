package com.ELShovi.service.implementation;

import com.ELShovi.model.Payment;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IPaymentRepository;
import com.ELShovi.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService extends GenericService<Payment,Integer> implements IPaymentService {
    private final IPaymentRepository repo;
    @Override
    protected IGenericRepository<Payment,Integer> getRepo() {
        return repo;
    }
}

