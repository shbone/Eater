package com.shbone.reggiedemo.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;
/**
 * @author sunhb
 */
@Slf4j
@Configuration
public class WebResourceConfig extends WebMvcConfigurationSupport {
    /**
     * 资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        /**
         * resource 下的 /backend/** 的资源映射到url localhost:xxx/backend/**下面
         */
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }


    // @Override
    // protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    //     log.info("配置消息转换器");
    //     MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    //     // java对象转换为json
    //     messageConverter.setObjectMapper(new JacksonObjectMapper());
    // //    提高转换器的优先级
    //     converters.add(0,messageConverter);
    //
    // }
}
