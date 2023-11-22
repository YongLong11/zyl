package com.zyl.something.proxyTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GreetHandler implements InvocationHandler {
    private Object delegate;

    public Object bind(Object delegate) {
        this.delegate = delegate;
        return Proxy.newProxyInstance(
                this.delegate.getClass().getClassLoader(), this.delegate
                        .getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        try {
            System.out.println("问候之前的日志记录...");
            // JVM通过这条语句执行原来的方法(反射机制)
            result = method.invoke(this.delegate, args);
        } catch (Exception e) {

        }
        return result;
    }
}
