package com.wsi.ticketsimulatorservices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedHeaders("*").allowCredentials(true);
    }

    /**
     * Q: 如果 只有寫 registry.addMapping("/**") 前端也可以打進來嗎
     * A: 默認的 allowedOrigins 是空的：這表示沒有任何外部來源被允許進行跨來源請求。
     *    默認的 allowedMethods 也是有限的：只有少數方法可能會被允許，但這些都不包括跨來源請求通常使用的方法。
     */

}
