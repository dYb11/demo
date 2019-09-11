package com.example.demo.xss;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class XssConfig {
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/comment/add","/xxbUser/add2");

        filterRegistrationBean.addInitParameter("paramName", "paramValue");
        filterRegistrationBean.setName("XssFilter");
        return filterRegistrationBean;
    }

}
