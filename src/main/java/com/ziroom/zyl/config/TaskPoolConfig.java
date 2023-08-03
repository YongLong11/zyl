package com.ziroom.zyl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName：TaskPoolConfig
 * @Description：
 * @Async 异步注解 未自定义时使用默认的线程池，即SimpleAsyncTaskExecutor
 * 可以通过三种方式实现自定义，1、实现 AsyncConfigurer 接口  2、继承  AsyncConfigurerSupport 类 ，3、自定义 taskExecutor 实现替换默认的
 * 执行时会去找默认的线程池，没有会去获取默认的线程池，获取时会优先根据 taskExecutor.class 寻找，没有时会去寻找 Executor.class 的默认线程池， 实现接口和继承类后，都会有相应的默认线程池
 * 尽量不要共用线程池
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/10 18:36
 **/

@EnableAsync
@Configuration
public class TaskPoolConfig{
    @Bean(name = "new_task")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //队列容量
        executor.setQueueCapacity(200);
        //活跃时间
        executor.setKeepAliveSeconds(60);
        //线程名字前缀
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
