package com.ziroom.zyl;


import com.ziroom.zyl.initLoad.beanNameGenerator.InterfaceAnnotationBeanNameGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, nameGenerator = InterfaceAnnotationBeanNameGenerator.class)
@EnableAsync
@EnableFeignClients
@EnableCaching
@MapperScan("com.ziroom.zyl.*.mapper")
public class ZylApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZylApplication.class, args);
    }

}
