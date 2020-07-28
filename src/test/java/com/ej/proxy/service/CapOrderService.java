package com.ej.proxy.service;

import com.ej.proxy.dto.OrderDto;

public class CapOrderService implements OrderService {
    @Override
    public void save(OrderDto orderDto) {
        if(query(orderDto.getOrderNo()) == null){
            System.out.println("保存帽子商品订单:"+orderDto.toString());
        }else {
            System.out.println("帽子商品订单已存在，无需保存:"+orderDto.toString());
        }
    }

    @Override
    public OrderDto query(String orderNo) {
        System.out.println("查询帽子商品订单:"+orderNo);
        if(orderNo.endsWith("0")){
            return null;
        }else {
            OrderDto orderDto = new OrderDto(orderNo);
            orderDto.setOrderNo(orderNo);
            return orderDto;
        }
    }
}
