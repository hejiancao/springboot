package com.example.i18n.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author created by shaos on 2020/8/18
 */
@Configuration
public class WebMvcConfig  {

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
