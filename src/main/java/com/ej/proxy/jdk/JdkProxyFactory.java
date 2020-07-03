package com.ej.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    public static <T> T getProxy(JdkProxyHandler<T> jdkProxyHandler) {
        return (T) Proxy.newProxyInstance(
                jdkProxyHandler.getTarget().getClass().getClassLoader(),
                jdkProxyHandler.getTarget().getClass().getInterfaces(),
                jdkProxyHandler
        );
    }

    public static <T> T getProxy(T target, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                invocationHandler
        );
    }
}
