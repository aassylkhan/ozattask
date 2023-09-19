package com.example.ozattask.config;

import com.example.ozattask.converter.StringToLongConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLongConverter());
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}