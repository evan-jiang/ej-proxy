package com.ej.proxy.service;

import com.ej.proxy.dto.OrderDto;

import java.util.UUID;

public class OrderServiceImpl2 {
    public boolean checkParams(OrderDto orderDto) {
        System.out.println("参数校验：");
        return true;
    }

    public String createOrder(OrderDto orderDto) {
        System.out.println("创建订单：");
        return UUID.randomUUID().toString();
    }

    public void sendSms(OrderDto orderDto) {
        System.out.println("发送短信：");
    }

    public final String toString(){
        System.out.println("toString：");
        return super.toString();
    }

    public final int hashCode(){
        System.out.println("hashCode：");
        return super.hashCode();
    }
}
