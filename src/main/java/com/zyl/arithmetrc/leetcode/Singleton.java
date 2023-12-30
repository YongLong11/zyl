package com.zyl.arithmetrc.leetcode;

public class Singleton {

    private Singleton() {
    }

    private static Singleton singleton;

    static {
        singleton = new Singleton();
    }

    public Singleton get() {
        return singleton;
    }


    public static Singleton get1() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }


    private static class SingletonManager {
        private static Singleton singleton = new Singleton();
    }

    public static Singleton get2(){
        return SingletonManager.singleton;
    }
}
