package com.grandcircus.spring.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by MichaelRiley on 5/21/17.
 */





    @Configuration
    @EnableWebMvc
    public class AppConfig extends WebMvcConfigurerAdapter {
        @Bean
        public InternalResourceViewResolver viewResolver()
        {
            InternalResourceViewResolver r = new InternalResourceViewResolver();
            r.setPrefix("WEB-INF/views/");
            r.setSuffix(".jsp");
            return r;
        }
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
            configurer.enable();
        }
    @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/"); }

    }




