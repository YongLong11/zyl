package com.zyl;


import com.zyl.initLoad.beanNameGenerator.InterfaceAnnotationBeanNameGenerator;
import com.zyl.something.httpAop.annotation.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}, nameGenerator = InterfaceAnnotationBeanNameGenerator.class)
@EnableAsync
@EnableFeignClients
@EnableCaching
@EnableHttpClient(basePackage = "com.zyl")
//@MapperScan("com.zyl.*.mapper")
public class ZylApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZylApplication.class, args);
    }

}
