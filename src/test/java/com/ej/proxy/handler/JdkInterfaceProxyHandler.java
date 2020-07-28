package com.ej.proxy.handler;

import com.ej.proxy.utils.PrintUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkInterfaceProxyHandler  implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = null;
        try {
            PrintUtils.print("执行目标方法前我可以干点啥呢？？？");
            //代理的是接口
            //可以自定义执行
            PrintUtils.print("代理接口，可以自定义执行代码(RPC,MyBatis...)");
        } catch (Exception e) {
            PrintUtils.print("执行目标方法出异常后我还可以干点啥呢？？？");
        } finally {
            PrintUtils.print("执行目标方法后我还可以干点啥呢？？？");
        }
        return invoke;
    }
}
