package com.ej.proxy.service;


import com.ej.proxy.dto.OrderDto;

public interface OrderService {

    void save(OrderDto orderDto);

    OrderDto query(String orderNo);
}
