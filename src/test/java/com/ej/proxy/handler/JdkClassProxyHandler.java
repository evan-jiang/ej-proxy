package com.ej.proxy.handler;

import com.ej.proxy.utils.PrintUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkClassProxyHandler<T>  implements InvocationHandler {

    /**
     * 被代理对象
     **/
    private T target;

    public JdkClassProxyHandler(T target) {
        this.target = target;
    }

    public T getTarget() {
        return target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = null;
        try {
            PrintUtils.print("执行目标方法前我可以干点啥呢？？？");
            invoke = method.invoke(this.target, args);
        } catch (Exception e) {
            PrintUtils.print("执行目标方法出异常后我还可以干点啥呢？？？");
        } finally {
            PrintUtils.print("执行目标方法后我还可以干点啥呢？？？");
        }
        return invoke;
    }
}
