package com.hbs.HBS;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HbsApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(HbsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO: Add any initialization code here
    }
}