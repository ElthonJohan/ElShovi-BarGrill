package com.ELShovi.service;

import com.ELShovi.model.Order;

public interface IOrderService extends IGenericService<Order,Integer> {

    boolean mesaOcupada(Integer idMesa);

}
