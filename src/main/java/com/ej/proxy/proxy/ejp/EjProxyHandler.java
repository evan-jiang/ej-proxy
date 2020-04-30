package com.ej.proxy.proxy.ejp;

import java.lang.reflect.Method;

public interface EjProxyHandler {

    void before(Object target, Method method ,Object[] args);

    Object current(Object target, Method method ,Object[] args);

    void throwable(Object target, Method method ,Object[] args,Throwable e);

    void after(Object target, Method method ,Object[] args);

}
