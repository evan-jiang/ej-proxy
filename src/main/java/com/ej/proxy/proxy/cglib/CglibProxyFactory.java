package com.ej.proxy.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibProxyFactory {

    public static <T> T getProxy(Class<T> clazz, MethodInterceptor methodInterceptor){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(methodInterceptor);
        return (T)enhancer.create();
    }
}
