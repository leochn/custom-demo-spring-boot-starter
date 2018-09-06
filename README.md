
# 自定义spring-boot-starter

## 创建maven项目,引入pom依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.vnext</groupId>
    <artifactId>custom-demo-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>

    <description>custom.demo.springboot.starter</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>

        <!-- 引入spring-boot-starter; 所有starter的基本配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <!-- 导入配置文件处理器，配置文件进行绑定就会有提示 -->
        <!-- 导入插件后,需要运行Application,然后才生效 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

</project>
```

## 自动配置功能的实现
### 核心配置类HelloServiceAutofiguration

    构建starter的关键是编写一个装配类，这个类可以提供该starter核心bean ;
    这里我们的starter提供一个实例bean，我们叫它为 HelloService ;
    负责对这个bean进行自动化装配的类叫做 HelloServiceAutofiguration ;
    保存application.properties配置信息的类叫做 HelloProperies ;
    这三种类像是铁三角一样，你可以在很多的spring-boot-starter中看到他们的身影 ;
    
    
我们首先来看 HelloServiceAutofiguration 的定义.
```java
@Configuration
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
```

spring容器会读取相应的配置信息到 HelloProperies 中 ,

然后依据调节判断初始化 HelloService 这个bean;

集成了该starter的项目就可以直接使用 HelloService 来存储键值信息了.

## 配置信息类 HelloProperies

存储配置信息的类 HelloProperies 很简单，源码如下所示:

```java
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
```

## starter提供功能的 HelloService

HelloService 类是提供整个starter的核心功能的类

```java
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
```

## 注解配置和spring.factories

自定义的starter有两种方式来通知spring容器导入自己的 auto-configuration类，也就是本文当中的 HelloServiceAutofiguration 类。

一般都是在starter项目的resources/META-INF文件夹下的spring.factories文件中加入需要自动化配置类的全限定名称。
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.vnext.custom.demo.starter.hello.HelloServiceAutofiguration,\
com.vnext.custom.demo.starter.utils.UtilsAutoconfiguration
```

spring boot项目中的EnableAutoConfigurationImportSelector会自动去每个jar的相应文件下查看spring.factories文件内容，

并将其中的类加载出来在auto-configuration过程中进行配置。而EnableAutoConfigurationImportSelector在@EnableAutoConfiguration注解中被import。

第一种方法只要是引入该starter，那么spring.factories中的auto-configuration类就会被装载，但是如果你希望有更加灵活的方式，那么就使用自定义注解来引入装配类。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HelloServiceAutofiguration.class)
@Documented
public @interface EnableHelloService {
    
}
```

有了这个注解，你可以在你引入该starter的项目中使用该注解，通过@import注解，spring容器会自动加载 HelloServiceAutofiguration 并自动化进行配置。


