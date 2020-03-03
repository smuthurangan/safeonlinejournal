package com.safe.online.journal;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/img/**",
                "/css/**",
                "/js/**",
                "/vendor/**",
                "/fonts/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/templates/css/",
                        "classpath:/templates/fonts/",
                        "classpath:/templates/images/",
                        "classpath:/templates/js/",
                        "classpath:/templates/vendor/",
                        "classpath:/templates/fonts/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }

}