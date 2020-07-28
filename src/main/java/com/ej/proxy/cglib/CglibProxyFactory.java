package com.ej.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibProxyFactory {

    public static <T> T getProxyByClass(Class<T> clazz, MethodInterceptor methodInterceptor){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(methodInterceptor);
        return (T)enhancer.create();
    }

    public static <T> T getProxyByInterface(Class<T> clazz, MethodInterceptor methodInterceptor){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setInterfaces(new Class[]{clazz});
        enhancer.setCallback(methodInterceptor);
        return (T)enhancer.create();
    }
}
