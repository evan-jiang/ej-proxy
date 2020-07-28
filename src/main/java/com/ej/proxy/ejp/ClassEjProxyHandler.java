package com.ej.proxy.ejp;

import java.lang.reflect.Method;

public abstract class ClassEjProxyHandler implements EjProxyHandler{

    @Override
    public final Object current(Object target, Method method, Object[] args) {
        throw new UnsupportedOperationException();
    }
}
