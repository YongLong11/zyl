package com.zyl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadFactoryConfig {

    /**
     * parallelExecutor
     * AbortPolicy: 拒绝策略,不指定为默认值
     * CallerRunsPolicy: 在任务被拒绝添加后，会调用当前线程池的所在的线程去执行被拒绝的任务
     * DiscardPolicy: 放弃策略,线程池拒绝的任务直接抛弃，不会抛异常也不会执行
     * DiscardOldestPolicy: 任务被拒绝添加时，会抛弃任务队列中最旧的任务也就是最先加入队列的，再把这个新任务添加进去。
     */
    @Bean(name = "parallelPool")
    public Executor threadForParallel(){
        int avail = Runtime.getRuntime().availableProcessors();
        return  new ThreadPoolExecutor(avail, 20, 10, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
