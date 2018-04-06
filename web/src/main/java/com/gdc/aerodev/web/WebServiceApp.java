package com.gdc.aerodev.web;

import com.gdc.aerodev.service.ServiceApp;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gdc.aerodev")
public class WebServiceApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceApp.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
