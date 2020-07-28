package com.ej.proxy.service;

import com.ej.proxy.dto.OrderDto;

public class ComputerOrderService {

    public void save(OrderDto orderDto) {
        if(query(orderDto.getOrderNo()) == null){
            System.out.println("保存电脑商品订单:"+orderDto.toString());
        }else {
            System.out.println("电脑商品订单已存在，无需保存:"+orderDto.toString());
        }
    }

    public OrderDto query(String orderNo) {
        System.out.println("查询电脑商品订单:"+orderNo);
        if(orderNo.endsWith("0")){
            return null;
        }else {
            OrderDto orderDto = new OrderDto(orderNo);
            return orderDto;
        }
    }
}
