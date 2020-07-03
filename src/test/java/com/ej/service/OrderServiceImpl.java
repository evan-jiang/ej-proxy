package com.ej.service;

import com.ej.dto.OrderDto;

public class OrderServiceImpl implements OrderService {
    @Override
    public boolean checkParams(OrderDto orderDto) {
        //System.out.println("参数校验：");
        return true;
    }

    @Override
    public String createOrder(OrderDto orderDto) {
        //System.out.println("创建订单：");
        return null;
    }

    @Override
    public void sendSms(OrderDto orderDto) {
        //System.out.println("发送短信：");
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
