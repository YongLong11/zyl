package com.zyl.service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Init {
    {
        System.out.println("这是 {}");
    }
    static {
        System.out.println("这是 static");

    }
    public Init(){
        new ThreadPoolExecutor(1, 2, 2, TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(1000), (r) -> new Thread(r, "thread-3-")
//     new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r, "thread-3-");
//            }
//        }
        , new ThreadPoolExecutor.DiscardOldestPolicy());
        System.out.println("这是构造函数");
    }

    public static class Init1 extends Init{
        {
            System.out.println("这是 Init1 {}");
        }
        static {
            System.out.println("这是 Init1 static");

        }
        public Init1(){
            System.out.println("这是 Init1 构造函数");
        }
    }

    public static void main(String[] args) {
        new Init1();
    }
}
