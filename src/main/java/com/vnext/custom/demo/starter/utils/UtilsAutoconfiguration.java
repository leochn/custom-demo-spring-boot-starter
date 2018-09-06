package com.vnext.custom.demo.starter.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leo
 * @version 1.0.0
 * @since 2018/9/6 14:15
 */
@Configuration
public class UtilsAutoconfiguration {

    @Bean
    public StringUtil StringUtil(){
        StringUtil stringUtil = new StringUtil();
        return stringUtil;
    }

}
