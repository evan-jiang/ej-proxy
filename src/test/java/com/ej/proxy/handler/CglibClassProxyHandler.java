package com.ej.proxy.handler;

import com.ej.proxy.utils.PrintUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibClassProxyHandler implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object invoke = null;
        try {
            PrintUtils.print("执行目标方法前我可以干点啥呢？？？");
            invoke = proxy.invokeSuper(obj, args);
        } catch (Exception e) {
            PrintUtils.print("执行目标方法出异常后我还可以干点啥呢？？？");
        } finally {
            PrintUtils.print("执行目标方法后我还可以干点啥呢？？？");
        }
        return invoke;
    }
}
