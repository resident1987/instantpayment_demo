package com.example.instantpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.instantpayment.repository")
public class InstantpaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstantpaymentApplication.class, args);
    }


}
