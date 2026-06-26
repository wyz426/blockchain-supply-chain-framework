package com.blc.scm.pass.config;

import com.blc.scm.pass.utils.CustomJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author Elaine Huang
 * @date 2024/2/25 5:29 PM
 * @signature Do it while you can!
 */
@Component
public class WebConfig extends WebMvcConfigurationSupport {
    @Autowired
    private CustomJsonHttpMessageConverter converter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter);
        super.configureMessageConverters(converters);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许所有方法
                .allowedHeaders("*");  // 允许所有请求头
    }
}
