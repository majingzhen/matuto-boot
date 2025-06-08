package com.matuto.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

/**
 * @author Matuto
 */
@SpringBootApplication
@MapperScan(basePackages = {
    "com.matuto.boot.**.mapper"
})
public class MatutoBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MatutoBootApplication.class, args);
    }
} 