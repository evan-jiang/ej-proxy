package com.ej.proxy.proxy.ejp;

import java.lang.reflect.Method;

public abstract class ClassEjProxyHandler implements EjProxyHandler{

    @Override
    public Object current(Object target, Method method, Object[] args) {
        throw new UnsupportedOperationException();
    }
}
