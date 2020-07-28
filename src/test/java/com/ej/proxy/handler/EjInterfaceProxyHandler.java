package com.ej.proxy.handler;

import com.ej.proxy.ejp.InterfaceEjProxyHandler;
import com.ej.proxy.utils.PrintUtils;

import java.lang.reflect.Method;

public class EjInterfaceProxyHandler extends InterfaceEjProxyHandler {
    @Override
    public void before(Object target, Method method, Object[] args) {
        PrintUtils.print("执行目标方法前我可以干点啥呢？？？");
    }

    @Override
    public Object current(Object target, Method method, Object[] args) {
        PrintUtils.print("代理接口，可以自定义执行代码(RPC,MyBatis...)");
        return null;
    }

    @Override
    public void throwable(Object target, Method method, Object[] args, Throwable e) {
        PrintUtils.print("执行目标方法出异常后我还可以干点啥呢？？？");
    }

    @Override
    public void after(Object target, Method method, Object[] args) {
        PrintUtils.print("执行目标方法后我还可以干点啥呢？？？");
    }
}
