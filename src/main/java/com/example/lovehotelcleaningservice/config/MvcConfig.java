package com.example.lovehotelcleaningservice.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("roomsList");
        registry.addViewController("/addUser").setViewName("addUser");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/adminPanel").setViewName("admin_panel/adminPanel");
        registry.addViewController("/adminPanel/addRoom").setViewName("addRoom");
    }
}