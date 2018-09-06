package com.vnext.custom.demo.starter.hello;

/**
 * @author leo
 * @version 1.0.0
 * @since 2018/9/6 13:17
 */
public class HelloService {

    HelloProperies helloProperies;

    public HelloProperies getHelloProperies() {
        return helloProperies;
    }

    public void setHelloProperies(HelloProperies helloProperies) {
        this.helloProperies = helloProperies;
    }

    public String sayHelloStarter(String name){
        return helloProperies.getPrefix()+ "," + name + "," + helloProperies.getSuffix();
    }
}
