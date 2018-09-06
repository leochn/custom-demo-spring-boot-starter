package com.vnext.custom.demo.starter.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author leo
 * @version 1.0.0
 * @since 2018/9/6 13:17
 */
@ConfigurationProperties(prefix = "vnext.custom.hello")
public class HelloProperies {

    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
