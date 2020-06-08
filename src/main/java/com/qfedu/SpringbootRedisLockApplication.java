package com.qfedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qfedu")
@MapperScan("com.qfedu.mapper")
public class SpringbootRedisLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisLockApplication.class, args);
    }

}
