package com.ej.service;

import com.ej.dto.OrderDto;

public interface OrderService {

    boolean checkParams(OrderDto orderDto);

    String createOrder(OrderDto orderDto);

    void sendSms(OrderDto orderDto);
}
