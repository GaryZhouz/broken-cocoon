package com.thoughtworks.shopbackend.components.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        final ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        // 后台需要有人登陆,账号密码配置
        Map<String, String> initParameters = new HashMap<>();

        // 增加配置
        initParameters.put("loginUsername", "admin");// 登陆key是固定的
        initParameters.put("loginPassword", "admin");

        // 允许谁能访问
        initParameters.put("allow", "");

        // 静止谁能访问  initParameters.put("test","192.168.1.1");
        bean.setInitParameters(initParameters);// 设置初始化参数
        return bean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new WebStatFilter());

        // 可以过滤那些请求
        Map<String, String> initParameters = new HashMap<>();

        // 这些东西不进行统计~
        initParameters.put("exclusion", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParameters);
        return bean;
    }

}
