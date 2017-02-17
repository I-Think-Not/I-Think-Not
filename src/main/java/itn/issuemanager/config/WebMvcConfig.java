package itn.issuemanager.config;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import itn.issuemanager.interceptor.BasicAuthInterceptor;
import itn.issuemanager.interceptor.BasicAuthProperties;

@Configuration
@EnableConfigurationProperties(BasicAuthProperties.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

	@Autowired
	BasicAuthProperties basicAuthProperties;
	
	@Bean
	public LoggingInterceptor loggingInterceptor(){
		return new LoggingInterceptor();
	}
	
	@Bean 
	public BasicAuthInterceptor basicAuthInterceptor() {
	    return new BasicAuthInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.debug("interceptor add");
		if(basicAuthProperties.isBasicAuthEnable())
			registry.addInterceptor(basicAuthInterceptor());
		
		registry.addInterceptor(loggingInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/","/user/login","/user/join","/user/new","/error", "/api/user/idCheck",
						"/api/user/findPw","/api/file/**","/user/findPw");
	}
	
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }
}
