package com.ej.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyHandler implements MethodInterceptor {

    @Override public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object invoke = null;
        try {
            System.out.println("执行目标方法前我可以干点啥呢？？？");
            invoke = proxy.invokeSuper(obj, args);
        } catch (Exception e) {
            System.out.println("执行目标方法出异常后我还可以干点啥呢？？？");
        } finally {
            System.out.println("执行目标方法后我还可以干点啥呢？？？");
        }
        return invoke;
    }
}
