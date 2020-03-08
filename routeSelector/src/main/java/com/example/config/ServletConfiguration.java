package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("com.example.controller")
public class ServletConfiguration implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("/res/");
        //registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }

    @Bean
    public ViewResolver resolver(){
        InternalResourceViewResolver resolvr = new InternalResourceViewResolver();
        resolvr.setViewClass(JstlView.class);
        resolvr.setPrefix("/WEB-INF/");
        resolvr.setSuffix(".jsp");
        return resolvr;
    }


}
