package com.ej.proxy.test;

import com.ej.proxy.cglib.CglibProxyFactory;
import com.ej.proxy.dto.OrderDto;
import com.ej.proxy.ejp.EjProxyFactory;
import com.ej.proxy.ejp.InterfaceEjProxyHandler;
import com.ej.proxy.handler.*;
import com.ej.proxy.jdk.JdkProxyFactory;
import com.ej.proxy.service.CapOrderService;
import com.ej.proxy.service.ComputerOrderService;
import com.ej.proxy.service.OrderService;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

public class Main {

    @Test
    public void jdk() {
        OrderDto orderDto = new OrderDto(UUID.randomUUID().toString());
        //代理有接口的类
        JdkClassProxyHandler<OrderService> jdkProxyHandler = new JdkClassProxyHandler<>(new CapOrderService());
        OrderService orderService = JdkProxyFactory.getProxy(jdkProxyHandler.getTarget(), jdkProxyHandler);
        orderService.save(orderDto);
        System.out.println();
        //代理接口
        orderService = JdkProxyFactory.getProxy(OrderService.class, new JdkInterfaceProxyHandler());
        orderDto = new OrderDto(UUID.randomUUID().toString());
        orderService.save(orderDto);
        System.out.println();
        System.out.println(orderService.toString());
    }

    @Test
    public void cglib() {
        OrderDto orderDto = new OrderDto(UUID.randomUUID().toString());
        //代理没有接口的类
        ComputerOrderService computerOrderService = CglibProxyFactory.getProxyByClass(ComputerOrderService.class, new CglibClassProxyHandler());
        computerOrderService.save(orderDto);
        System.out.println();
        //代理有接口的类
        OrderService orderService = CglibProxyFactory.getProxyByClass(CapOrderService.class, new CglibClassProxyHandler());
        orderDto = new OrderDto(UUID.randomUUID().toString());
        orderService.save(orderDto);
        System.out.println();
        //代理接口
        orderService = CglibProxyFactory.getProxyByInterface(OrderService.class, new CglibInterfaceProxyHandler());
        orderDto = new OrderDto(UUID.randomUUID().toString());
        orderService.save(orderDto);
    }

    @Test
    public void ejp() throws Exception {
        OrderDto orderDto = new OrderDto(UUID.randomUUID().toString());
        //代理没有接口的类
        ComputerOrderService computerOrderService = EjProxyFactory.newEjProxy(new ComputerOrderService(), new EjpClassProxyHandler());
        computerOrderService.save(orderDto);
        System.out.println();
        //代理有接口的类
        OrderService orderService = EjProxyFactory.newEjProxy(new CapOrderService(), new EjpClassProxyHandler());
        orderDto = new OrderDto(UUID.randomUUID().toString());
        orderService.save(orderDto);
        System.out.println();
        //代理接口
        orderService = EjProxyFactory.newInterfaceEjProxy(OrderService.class, new EjpInterfaceProxyHandler());
        orderDto = new OrderDto(UUID.randomUUID().toString());
        orderService.save(orderDto);
    }

    @Test
    public void compareInterface() throws Exception {
        OrderDto orderDto = new OrderDto(UUID.randomUUID().toString());
        int times = 1000000;
        OrderService jdkOrderService = JdkProxyFactory.getProxy(OrderService.class, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
        OrderService cglibOrderService = CglibProxyFactory.getProxyByInterface(OrderService.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return null;
            }
        });
        OrderService ejOrderService = EjProxyFactory.newInterfaceEjProxy(OrderService.class, new InterfaceEjProxyHandler() {
            @Override
            public void before(Object target, Method method, Object[] args) {

            }

            @Override
            public Object current(Object target, Method method, Object[] args) {
                return null;
            }

            @Override
            public void throwable(Object target, Method method, Object[] args, Throwable e) {

            }

            @Override
            public void after(Object target, Method method, Object[] args) {

            }
        });
        System.out.println("JDK代理消耗时间:" + getExecuteTime(jdkOrderService, orderDto, times));
        System.out.println("CGLIB代理消耗时间:" + getExecuteTime(cglibOrderService, orderDto, times));
        System.out.println("EJ代理消耗时间:" + getExecuteTime(ejOrderService, orderDto, times));
    }

    private Long getExecuteTime(OrderService orderService, OrderDto orderDto, int times) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            orderService.save(orderDto);
        }
        return System.currentTimeMillis() - start;
    }

    @Test
    public void compareClass() throws Exception {
        OrderDto orderDto = new OrderDto("00000");
        int times = 50000000;
        JdkClassProxyHandler<OrderService> jdkClassProxyHandler = new JdkClassProxyHandler<>(new CapOrderService());
        OrderService jdkOrderService = JdkProxyFactory.getProxy(jdkClassProxyHandler.getTarget(), jdkClassProxyHandler);
        OrderService cglibOrderService = CglibProxyFactory.getProxyByClass(CapOrderService.class, new CglibClassProxyHandler());
        OrderService ejOrderService = EjProxyFactory.newEjProxy(new CapOrderService(),new EjpClassProxyHandler());
        Long jdk = getExecuteTime(jdkOrderService, orderDto, times);
        Long cglib = getExecuteTime(cglibOrderService, orderDto, times);
        Long ej = getExecuteTime(ejOrderService, orderDto, times);
        System.out.println("JDK代理消耗时间:" + jdk);
        System.out.println("CGLIB代理消耗时间:" + cglib);
        System.out.println("EJ代理消耗时间:" + ej);
    }


    @Test
    public void compareClasses() throws Exception {
        int times = 10;
        for (int i = 0; i < times; i++) {
            compareClass();
            System.out.println();
        }
    }
}
