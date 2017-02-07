package itn.issuemanager.config;


import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

	@Bean
	public LoggingInterceptor loggingInterceptor(){
		return new LoggingInterceptor();
	}
		
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		log.debug("interceptor add");
		registry.addInterceptor(loggingInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/","/user/login","/user/join","/user/new","/error");
	}

}
