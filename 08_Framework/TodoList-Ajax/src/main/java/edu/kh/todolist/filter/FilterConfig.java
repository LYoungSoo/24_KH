package edu.kh.todolist.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CommonFilter> commonFilter() {
        FilterRegistrationBean<CommonFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CommonFilter());
        registrationBean.addUrlPatterns("/*"); // Define URL patterns to apply the filter
        return registrationBean;
    }
}
