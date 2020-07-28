package com.ej.proxy.service;

import com.ej.proxy.dto.OrderDto;
import com.ej.proxy.utils.PrintUtils;

public class ComputerOrderService {

    public void save(OrderDto orderDto) {
        if(query(orderDto.getOrderNo()) == null){
            PrintUtils.print("保存电脑商品订单:"+orderDto.toString());
        }else {
            PrintUtils.print("电脑商品订单已存在，无需保存:"+orderDto.toString());
        }
    }

    public OrderDto query(String orderNo) {
        PrintUtils.print("查询电脑商品订单:"+orderNo);
        if(orderNo.endsWith("0")){
            return null;
        }else {
            OrderDto orderDto = new OrderDto(orderNo);
            return orderDto;
        }
    }
}
