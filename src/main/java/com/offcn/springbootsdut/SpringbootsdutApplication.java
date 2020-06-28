package com.offcn.springbootsdut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.offcn.springbootsdut.mapper")
public class SpringbootsdutApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootsdutApplication.class, args);
    }
}
