package com.ej.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    public static <T> T getProxy(T target,InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                invocationHandler
        );
    }

    public static <T> T getProxy(Class<?> interfaceClass,InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(
                JdkProxyFactory.class.getClassLoader(),
                new Class[]{interfaceClass},
                invocationHandler
        );
    }
}
