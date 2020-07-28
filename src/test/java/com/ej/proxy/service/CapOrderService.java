package com.ej.proxy.service;

import com.ej.proxy.dto.OrderDto;
import com.ej.proxy.utils.PrintUtils;

public class CapOrderService implements OrderService {
    @Override
    public void save(OrderDto orderDto) {
        if(query(orderDto.getOrderNo()) == null){
            PrintUtils.print("保存帽子商品订单:"+orderDto.toString());
        }else {
            PrintUtils.print("帽子商品订单已存在，无需保存:"+orderDto.toString());
        }
    }

    @Override
    public OrderDto query(String orderNo) {
        PrintUtils.print("查询帽子商品订单:"+orderNo);
        if(orderNo.endsWith("0")){
            return null;
        }else {
            OrderDto orderDto = new OrderDto(orderNo);
            orderDto.setOrderNo(orderNo);
            return orderDto;
        }
    }
}
