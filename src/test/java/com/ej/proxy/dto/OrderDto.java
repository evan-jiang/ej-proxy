package com.ej.proxy.dto;

public class OrderDto {
    private String orderNo;

    public OrderDto(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                " orderNo='" + orderNo + '\'' +
                '}';
    }
}
