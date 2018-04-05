package com.gdc.aerodev.web;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AerodevApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AerodevApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
