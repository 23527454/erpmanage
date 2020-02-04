package com.demo.erpmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.demo.erpmanage.mapper")
public class ErpmanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpmanageApplication.class, args);
    }

}
