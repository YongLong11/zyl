package com.zyl.something.proxyTest;

public class GreetImpl implements Greet{
    public GreetImpl(){
    }

//    public GreetImpl createGreetImpl(){
//        //greetHandler的作用就是实现InvocationHandler接口实现代理功能，
//        // 并在greetHandler方法的invoke中去实现自己需要的业务功能
//        GreetHandler greetHandler = new GreetHandler();
//        //将greet设置进 代理类中，实现在此作用域中GreetImpl实例greet的所有调用都会经过GreetHandler代理转发
//        return (GreetImpl) greetHandler.bind(this);
//    }

    @Override
    public void sayHello() {
        System.out.println("this is hello");
    }

    @Override
    public void sayBay() {
        System.out.println("this is good bay");
    }
}
