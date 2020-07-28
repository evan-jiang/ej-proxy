package com.ej.proxy.test;

import com.ej.proxy.cglib.CglibProxyFactory;
import com.ej.proxy.cglib.CglibProxyHandler;
import com.ej.proxy.dto.OrderDto;
import com.ej.proxy.ejp.ClassEjProxyHandler;
import com.ej.proxy.ejp.EjProxyFactory;
import com.ej.proxy.jdk.JdkProxyFactory;
import com.ej.proxy.jdk.JdkProxyHandler;
import com.ej.proxy.service.CapOrderService;
import com.ej.proxy.service.ComputerOrderService;
import com.ej.proxy.service.OrderService;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.UUID;

public class Main {

    @Test
    public void jdk() {
        OrderDto orderDto = new OrderDto( UUID.randomUUID().toString());
        OrderService orderService = JdkProxyFactory.getProxy(new JdkProxyHandler<OrderService>(new CapOrderService()));
        orderService.save(orderDto);
    }

    @Test
    public void cglib() {
        OrderDto orderDto = new OrderDto(UUID.randomUUID().toString());
        ComputerOrderService computerOrderService = CglibProxyFactory.getProxy(ComputerOrderService.class, new CglibProxyHandler());
        computerOrderService.save(orderDto);
        System.out.println("\n\n");
        OrderService orderService = CglibProxyFactory.getProxy(CapOrderService.class,new CglibProxyHandler());
        orderDto = new OrderDto(UUID.randomUUID().toString());
        orderService.save(orderDto);
    }

    @Test
    public void ej() throws Exception {
        OrderDto orderDto = new OrderDto(UUID.randomUUID().toString());
        ComputerOrderService computerOrderService = EjProxyFactory.newEjProxy(new ComputerOrderService(), new ClassEjProxyHandler() {
            @Override
            public void before(Object target, Method method, Object[] args) {
                System.out.println("执行目标方法前我可以干点啥呢？？？");
            }

            @Override
            public void throwable(Object target, Method method, Object[] args, Throwable e) {
                System.out.println("执行目标方法出异常后我还可以干点啥呢？？？");
            }

            @Override
            public void after(Object target, Method method, Object[] args) {
                System.out.println("执行目标方法后我还可以干点啥呢？？？");
            }
        });
        computerOrderService.save(orderDto);
        Thread.sleep(1000000L);
    }
}
