package com.ej.proxy.service;

import com.ej.proxy.dto.OrderDto;

public class ShoeOrderService implements OrderService {
    @Override
    public void save(OrderDto orderDto) {
        if(query(orderDto.getOrderNo()) == null){
            System.out.println("保存鞋子商品订单:"+orderDto.toString());
        }else {
            System.out.println("鞋子商品订单已存在，无需保存:"+orderDto.toString());
        }
    }

    @Override
    public OrderDto query(String orderNo) {
        System.out.println("查询鞋子商品订单:"+orderNo);
        if(orderNo.endsWith("0")){
            return null;
        }else {
            OrderDto orderDto = new OrderDto(orderNo);
            return orderDto;
        }
    }
}
