package com.ej.proxy.service;

import com.ej.proxy.dto.OrderDto;

public interface OrderService {

    boolean checkParams(OrderDto orderDto);

    String createOrder(OrderDto orderDto);

    void sendSms(OrderDto orderDto);
}
