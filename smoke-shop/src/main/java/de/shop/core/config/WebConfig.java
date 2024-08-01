package de.shop.core.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;//For swagger
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.10.3/swagger-initializer.js")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.10.3/index.html")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.10.3/swagger-ui.css")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.10.3/index.css")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.10.3/swagger-ui-bundle.js")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.10.3/swagger-ui-standalone-preset.js");
    }

}
