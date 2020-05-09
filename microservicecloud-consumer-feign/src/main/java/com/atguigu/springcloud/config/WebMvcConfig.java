package com.atguigu.springcloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if(!registry.hasMappingForPattern("/static/**")){
            registry.addResourceHandler("/image/**").addResourceLocations("file:C:\\Users\\liujun\\Desktop\\bishe\\biyeshiji-master\\microservicecloud-consumer-feign\\src\\main\\resources\\static\\img\\"); /*绝对路径*/
        }
        super.addResourceHandlers(registry);
    }

}