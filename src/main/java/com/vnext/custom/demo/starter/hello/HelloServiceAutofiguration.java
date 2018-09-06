package com.vnext.custom.demo.starter.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leo
 * @version 1.0.0
 * @since 2018/9/6 13:18
 */
@Configuration
@ConditionalOnClass(HelloService.class)
@ConditionalOnWebApplication //web用于才生效
@EnableConfigurationProperties(HelloProperies.class)
public class HelloServiceAutofiguration {

    @Autowired
    private HelloProperies helloProperies;

    @Bean
    public HelloService helloService(){
        HelloService helloService = new HelloService();
        helloService.setHelloProperies(helloProperies);
        return helloService;
    }
}
