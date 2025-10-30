package com.ELShovi.service.implementation;

import com.ELShovi.model.Reservation;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IReservationRepository;
import com.ELShovi.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService extends GenericService<Reservation,Integer> implements IReservationService {
    private final IReservationRepository repo;
    @Override
    protected IGenericRepository<Reservation,Integer> getRepo() {
        return repo;
    }
}