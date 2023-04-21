package net.posco.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {	
		registry.addResourceHandler("/resources/static/**", 
			"/upload/picture/user/profile-picture/**").addResourceLocations("file:upload/picture/user/profile-picture/");
	}
    
}
