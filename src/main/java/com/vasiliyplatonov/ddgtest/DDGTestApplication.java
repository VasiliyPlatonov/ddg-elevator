package com.vasiliyplatonov.ddgtest;

import com.vasiliyplatonov.ddgtest.domain.Elevator;
import com.vasiliyplatonov.ddgtest.domain.ElevatorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class DDGTestApplication implements WebMvcConfigurer {
    @Bean
    public Elevator elevator() {
        return new ElevatorImpl(1,7);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("index").setViewName("index");
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DDGTestApplication.class, args);

    }

}
