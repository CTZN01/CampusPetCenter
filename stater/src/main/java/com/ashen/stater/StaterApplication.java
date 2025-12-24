package com.ashen.stater;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ashen")
@MapperScan("com.ashen.**.mapper")
public class StaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaterApplication.class, args);
    }

}
