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
}
